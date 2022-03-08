import React from 'react';
import {BlackButton} from "../../ui/buttons";

const MarketItem = ({item, slot, buyItem}) => {
    return (
        <div className="grid grid-rows-4 gap-2 rounded-3xl bg-black justify-center ring-inset ring-1 ring-white pb-2">
            <dib className="text-center text-teal-200 text-lg font-mono pt-3">{item.title}</dib>
            <div className="text-center text-gray-100 text-md font-mono">{item.description}</div>
            <div className="text-center text-teal-200 text-2xl font-extrabold font-mono">{slot.price}$</div>
            <BlackButton title="Buy" onClick={() => buyItem(item.id)}/>
        </div>
    );
};

export default MarketItem;