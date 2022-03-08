import React, {useContext, useEffect, useState} from 'react';
import {deposit, getProfile} from "../../api";
import {UserContext} from "../../context/UserContext";
import UserInfo from "./UserInfo";
import Deposit from "./Deposit";

const Profile = () => {
    const [token] = useContext(UserContext)
    const [user, setUser] = useState({login: '', balance: 0})
    const [amount, setAmount] = useState('0')

    useEffect(() => {
        getProfile(token)
            .then(response => setUser(response.data))
    }, [token])


    function makeDeposition(e) {
        e.preventDefault()
        deposit(token, parseFloat(amount))
            .then(response => {
                console.log(response.data)
                setUser({...user, balance: response.data})
                setAmount('0')
            })
            .catch(() => {
                alert("Amount could not be negative")
                setAmount('0')
            })
    }

    return (
        <div className="p-20">
            <div className="grid grid-rows-2 place-items-center space-y-20">
                <UserInfo user={user}/>
                <Deposit amount={amount} setAmount={setAmount} onClick={makeDeposition}/>
            </div>
        </div>
    );
};

export default Profile;