import React, {useEffect, useState} from 'react';
import {getProfile} from "../api";

export const TOKEN_KEY = "TOKEN_KEY"

export const UserContext = React.createContext(localStorage.getItem(TOKEN_KEY));

export const UserProvider = ({children}) => {
    const [token, setToken] = useState(localStorage.getItem(TOKEN_KEY))

    useEffect(() => {
        getProfile(token)
            .then(response => {
                if (response.status !== 200) {
                    setToken(null)
                }
                localStorage.setItem(TOKEN_KEY, token)
            })
            .catch(e => console.log(e))
    }, [token])
    return <UserContext.Provider value={[token, setToken]}>
        {children}
    </UserContext.Provider>
}