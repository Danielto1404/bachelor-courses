import React, {useContext, useState} from 'react';
import {BlackButton} from "../ui/buttons";
import {UserContext} from "../../context/UserContext";
import Profile from "../profile/Profile";
import CreateItem from "../create/CreateItem";
import MyItemsPage from "../items/myitems/MyItemsPage";
import LaidPage from "../items/laid/LaidPage";
import MarketPage from "../items/market/MarketPage";

const NavBar = () => {

    const [, setToken] = useContext(UserContext)
    const [state, setState] = useState("profile")

    const getWindowState = () => {
        switch (state) {
            case "profile":
                return <Profile/>
            case "create":
                return <CreateItem/>
            case "on sale":
                return <LaidPage/>
            case "my items":
                return <MyItemsPage/>
            case "buy":
                return <MarketPage/>
            default:
                return <div>HW</div>
        }
    }

    return (
        <div>
            <div className="flex text-lg flex-row place-items-start justify-between pt-4 px-4 pb-10">
                <div className="text-lg flex items-start space-x-5">
                    <BlackButton title="Create" onClick={() => setState('create')}/>
                    <BlackButton title="Market" onClick={() => setState('buy')}/>
                    <BlackButton title="My Items" onClick={() => setState('my items')}/>
                    <BlackButton title="On Sale" onClick={() => setState('on sale')}/>
                </div>
                <div className="grid grid-rows-2 gap-2">
                    <BlackButton title="Profile" onClick={() => setState('profile')}/>
                    <BlackButton title="Logout" onClick={() => setToken(null)}/>
                </div>
            </div>
            {getWindowState()}
        </div>

    );
};

export default NavBar;