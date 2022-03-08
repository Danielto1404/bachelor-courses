import React, {useContext} from 'react';
import Auth from "./components/main/Auth";
import {UserContext} from "./context/UserContext";
import NavBar from "./components/navbar/NavBar";

const AppRouter = () => {
    const [token] = useContext(UserContext)
    return (
        <div>
            {token
                ? <NavBar/>
                : <Auth/>
            }
        </div>
    )
};

export default AppRouter;