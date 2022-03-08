import React from 'react';

export const BlackButton = ({title, onClick, ...props}) => {
    return (
        <button
            onClick={onClick}
            className="px-3 py-1 rounded-lg bg-black text-white font-semibold font-lg
                           hover:text-rose-300 hover:bg-black"
            {...props}
        >
            {title}
        </button>
    );
};


export const WhiteButton = ({title, onClick, ...props}) => {
    return (
        <button
            onClick={onClick}
            className="px-3 py-1 rounded-lg bg-white text-black font-semibold font-lg
                           hover:text-rose-300 hover:bg-black"
            {...props}
        >
            {title}
        </button>
    );
};

export const PurpleButton = ({title, onClick, ...props}) => {
    return (
        <button
            onClick={onClick}
            className="px-3 rounded-3xl bg-white text-white bg-purple-500 font-lg font-extrabold hover:text-pink-300"
            {...props}
        >
            {title}
        </button>
    );
}