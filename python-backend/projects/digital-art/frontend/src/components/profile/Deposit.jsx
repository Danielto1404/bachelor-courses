import React from 'react';
import Input from "../auth/Input";
import {BlackButton} from "../ui/buttons";

const Deposit = ({amount, setAmount, onClick}) => {

    return (
        <div className="space-x-4">
            <Input type="number" placeholder="0" value={amount} onChange={e => setAmount(e.target.value)}/>
            <BlackButton title="deposit" onClick={onClick}/>
        </div>
    );
};

export default Deposit;