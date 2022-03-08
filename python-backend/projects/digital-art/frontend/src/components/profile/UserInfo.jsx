import React from 'react';

const UserInfo = ({user}) => {
    return (
        <div className="space-y-5">
            <div className="text-2xl font-mono text-white">
                <label>Welcome back</label>
                <label className="text-extrabold text-5xl text-teal-100"> {user.login}</label>
            </div>
            <div className="text-4xl font-mono text-extrabold">Balance: {user.balance}$</div>
        </div>
    );
};

export default UserInfo;