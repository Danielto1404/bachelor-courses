import React from 'react';

const Input = ({placeholder, children, ...props}) => {
    return (
        <input className="px-2 py-1 rounded-lg focus:outline-none focus:ring-2 focus:ring-gray-900"
               placeholder={placeholder}
               {...props}/>
    );
};

export default Input;