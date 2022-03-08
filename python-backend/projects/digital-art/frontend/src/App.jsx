import './css/style.css'

import React from 'react';
import {UserProvider} from "./context/UserContext";
import AppRouter from "./AppRouter";

const App = () => {
    return (
        <UserProvider>
            <AppRouter/>
        </UserProvider>
    );
};

export default App;
