import React, {useContext, useEffect, useState} from 'react';
import classes from '../../css/background.module.css'
import {BlackButton, WhiteButton} from "../ui/buttons";
import {signIn, signUp, welcomeMessage} from "../../api";
import {UserContext} from "../../context/UserContext";
import UserForm from "../auth/UserForm";

const Auth = () => {

    const [user, setUser] = useState({login: '', password: ''})
    const [state, setState] = useState('guest')
    const [title, setTitle] = useState('')
    const [, setToken] = useContext(UserContext)
    const [error, setError] = useState('')

    useEffect(() => {
        welcomeMessage()
            .then(response => setTitle(response.data))
            .catch(error => alert(error))
    }, [])

    const makeSignIn = async (e) => {
        e.preventDefault()
        signIn(user)
            .then(response => {
                setToken(response.data['access_token'])
                setError('')
            })
            .catch(() => setError("Invalid credentials"))
    }

    const makeSignUp = async (e) => {
        e.preventDefault()
        signUp(user)
            .then(response => {
                setToken(response.data['access_token'])
                setUser({login: '', password: ''})
                setError('')
                setState('login')
            })
            .catch(() => setError("FAILED"))
    }

    return (
        <div className={classes.gifBackground}>
            <div className="text-center h-screen w-screen">
                <div
                    className="p-20 text-3xl text-center font-extrabold text-transparent bg-clip-text
                           md:text-6xl bg-gradient-to-br from-green-50 to-white">
                    {title}
                </div>
                {
                    state === 'guest'
                        ? <div className="flex flex-row justify-center gap-10 text-lg font-mono">
                            <BlackButton title="Sign In" onClick={() => setState('login')}/>
                            <BlackButton title="Sign Up" onClick={() => setState('register')}/>
                        </div>
                        : <div className="space-y-5">
                            <UserForm user={user}
                                      setUser={setUser}
                                      error={error}
                                      setError={setError}
                                      buttonTitle={state === 'login' ? 'Sign In' : 'Sign Up'}
                                      onClick={state === 'login' ? makeSignIn : makeSignUp}/>
                            <WhiteButton title={state === 'login' ? 'Sign Up' : 'Sign In'} onClick={() => {
                                setError('')
                                setState(state === 'login' ? 'register' : 'login')
                            }}/>
                        </div>
                }
            </div>
        </div>
    );
};

export default Auth;