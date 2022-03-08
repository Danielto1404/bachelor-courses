import React, {useContext, useState} from 'react';
import {BlackButton} from "../ui/buttons";
import Input from "../auth/Input";
import {createItem} from "../../api";
import {UserContext} from "../../context/UserContext";

const CreateItem = () => {

    const [token] = useContext(UserContext)

    const [item, setItem] = useState({title: '', description: ''})


    const pushCreation = () => {
        createItem(token, item)
            .then(() => alert("Items created. See it in collection."))
            .catch(() => alert("Unable to create item"))
            .finally(() => setItem({title: '', description: ''}))
    }

    return (
        <div className="flex justify-center items-center p-10">
            <form className="grid grid-rows-3 gap-3">
                <Input placeholder="Title"
                       value={item.title}
                       onChange={e => setItem({...item, title: e.target.value})}/>
                <Input placeholder="Description"
                       value={item.description}
                       onChange={e => setItem({...item, description: e.target.value})}/>
                <BlackButton title="Create" onClick={(e) => {
                    e.preventDefault();
                    pushCreation()
                }}/>
            </form>
        </div>
    );
};

export default CreateItem;