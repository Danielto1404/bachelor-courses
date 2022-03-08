import React, {useContext, useEffect, useState} from 'react';
import {UserContext} from "../../../context/UserContext";
import EmptyItems from "../EmptyItems";
import {deleteItem, getItems, sellItem, transferItem} from "../../../api";
import MyItem from "./MyItem";

const MyItemsPage = () => {

    const [token] = useContext(UserContext)
    const [isError, setIsError] = useState(false)
    const [items, setItems] = useState([])

    const processSell = (item) => {
        const price = parseFloat(prompt("Cost you want this item to be sold?"))
        if (isNaN(price) || price < 0) {
            alert("Price should be any positive number");
            return
        }

        const sellItemBody = {item_id: item.id, price}
        sellItem(token, sellItemBody)
            .then(() => {
                setItems(items.filter(x => x.id !== item.id))
                alert("Items transferred to market!")
            })
            .catch(() => alert("Unable to sell this item"))
    }

    const processTransfer = (item) => {
        const address = prompt("Input deposit address")
        const transferItemBody = {to_user: address, item_id: item.id}
        transferItem(token, transferItemBody)
            .then(() => {
                alert(`Items transferred to ${address}`)
                setItems(items.filter(x => x.id !== item.id))
            })
            .catch(() => alert("Unable to transfer this item"))
    }

    const processItemDeletion = (item) => {
        deleteItem(token, item.id)
            .then(r => {
                r.data
                    ? setItems(items.filter(x => x.id !== item.id))
                    : alert("Unable to delete item")
            })
            .catch(() => alert("Unable to delete item"))
    }

    useEffect(() => {
        getItems(token)
            .then(response => {
                setItems(response.data)
            })
            .catch(() => setIsError(true))
    }, [token])

    return (
        <div>
            {isError || items.length === 0
                ? <EmptyItems/>
                : <div className="grid grid-cols-3 justify-center gap-10 p-5">
                    {items.map(item =>
                        <MyItem key={item.id}
                                item={item}
                                deleteItem={processItemDeletion}
                                transfer={processTransfer}
                                sell={processSell}
                        />
                    )}
                </div>
            }
        </div>
    );
};

export default MyItemsPage;