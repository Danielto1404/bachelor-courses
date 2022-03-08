import React, {useContext, useEffect, useState} from 'react';
import {UserContext} from "../../../context/UserContext";
import EmptyItems from "../EmptyItems";
import {getLaidItems, rollbackToCollection, updatePrice} from "../../../api";
import LaidItem from "./LaidItem";

const LaidPage = () => {

    const [token] = useContext(UserContext)
    const [isError, setIsError] = useState(false)
    const [laidItems, setItems] = useState([])


    const returnToCollection = (item_id) => {
        rollbackToCollection(token, item_id)
            .then(() => setItems(laidItems.filter(x => x.item.id !== item_id)))
            .catch(() => alert("Unable to rollback item"))
    }

    const changePrice = (item_id) => {
        const price = parseFloat(prompt("Enter a new price"))
        if (isNaN(price) || price < 0) {
            alert('Price should be any positive number')
            return
        }
        const paymentOptions = {item_id, price}
        updatePrice(token, paymentOptions)
            .then(response => {
                const newPrice = response.data
                setItems([
                    ...laidItems.filter(x => x.item.id !== item_id),
                    {
                        ...laidItems.filter(x => x.item.id === item_id)[0],
                        slot: {
                            price: newPrice
                        }
                    }])
            })
            .catch(() => alert("Unable to update price"))
    }

    useEffect(() => {
        getLaidItems(token)
            .then(response => {
                setIsError(response.data.length === 0)
                setItems(response.data)
            })
            .catch(() => setIsError(true))
    }, [token])

    return (
        <div>
            {isError
                ? <EmptyItems/>
                : <div className="grid grid-cols-3 justify-center gap-10 p-5">
                    {laidItems.map(laidItem =>
                        <LaidItem key={laidItem.item.id}
                                  laidItem={laidItem}
                                  changePrice={changePrice}
                                  deleteFromMarket={returnToCollection}
                        />
                    )}
                </div>
            }
        </div>
    );
};

export default LaidPage;