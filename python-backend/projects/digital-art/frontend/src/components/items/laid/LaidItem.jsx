import React from 'react';
import {BlackButton} from "../../ui/buttons";

const LaidItem = ({laidItem, changePrice, deleteFromMarket}) => {
    return (
        <div className="grid grid-rows-4 gap-2 rounded-3xl bg-black justify-center ring-inset ring-1 ring-white pb-2">
            <dib className="text-center text-teal-200 text-lg font-mono pt-3">{laidItem.item.title}</dib>
            <div className="text-center text-gray-100 text-md font-mono">{laidItem.item.description}</div>
            <div className="text-center text-teal-200 text-2xl font-extrabold font-mono">{laidItem.slot.price}$</div>
            <div className="space-x-5 pb-2 px-1">
                <BlackButton title="Change price" onClick={() => changePrice(laidItem.item.id)}/>
                <BlackButton title="To collection" onClick={() => deleteFromMarket(laidItem.item.id)}/>
            </div>
        </div>
    );
};

export default LaidItem;