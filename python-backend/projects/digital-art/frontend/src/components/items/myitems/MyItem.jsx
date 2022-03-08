import React from 'react';
import {BlackButton, PurpleButton} from "../../ui/buttons";

const MyItem = ({item, transfer, sell, deleteItem}) => {
    return (
        <div className="grid grid-rows-4 gap-2 rounded-3xl bg-black justify-center ring-inset ring-1 ring-white pb-2">
            <dib className="text-center text-teal-200 text-lg font-mono pt-3">{item.title}</dib>
            <div className="text-center text-gray-100 text-md font-mono">{item.description}</div>
            <div className="space-x-5 pb-2">
                <BlackButton title="Transfer" onClick={() => transfer(item)}/>
                <BlackButton title="Sell" onClick={() => sell(item)}/>
            </div>
            <PurpleButton title="Delete" onClick={() => deleteItem(item)}/>
        </div>
    );
};

export default MyItem;