import React, {useContext, useEffect, useState} from 'react';
import {UserContext} from "../../../context/UserContext";
import EmptyItems from "../EmptyItems";
import MarketItem from "./MarketItem";
import {buyItem, getOnSaleItems} from "../../../api";

const MarketPage = () => {

    const [token] = useContext(UserContext)
    const [isError, setIsError] = useState(false)
    const [slotItems, setItems] = useState([])


    const processPayment = (item_id) => {
        buyItem(token, item_id)
            .then(() => setItems(slotItems.filter(x => x.item.id !== item_id)))
            .catch(() => alert("Check if you have enough balance to buy"))
    }

    useEffect(() => {
        getOnSaleItems(token)
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
                    {slotItems.map(slotItem =>
                        <MarketItem key={slotItem.item.id}
                                    item={slotItem.item}
                                    slot={slotItem.slot}
                                    buyItem={processPayment}
                        />
                    )}
                </div>
            }
        </div>
    );
};

export default MarketPage;