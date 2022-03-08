import React from 'react';
import Input from "./Input";
import {BlackButton} from "../ui/buttons";

const UserForm = ({user, setUser, buttonTitle, error, setError, onClick}) => {
    return (
        <div className="flex justify-center items-center p-4">
            <form className="grid grid-rows-3 gap-4">
                <Input value={user.login}
                       placeholder="login"
                       onChange={(e => {
                           setUser({...user, login: e.target.value})
                           setError('')
                       })}/>
                <Input value={user.password}
                       placeholder="password"
                       onChange={(e => {
                           setUser({...user, password: e.target.value})
                           setError('')
                       })}/>
                <label className="text-red-blue-900 font-extrabold">{error}</label>
                <BlackButton title={buttonTitle} onClick={onClick}/>
            </form>
        </div>
    );
};

export default UserForm;