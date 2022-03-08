import axios from "axios";


export const welcomeMessage = () => {
    return axios.get("/api/")
}

// Auth
export const signUp = (user) => {
    return axios.post("/api/users/", user)
}

export const signIn = (user) => {
    return axios.post("/api/users/auth",
        JSON.stringify(
            `grant_type=&username=${user.login}&password=${user.password}&scope=&client_id=&client_secret=`
        ), {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        })
}

export const getProfile = (token) => {
    return axios.get("/api/users/me", {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}

// Balance
export const deposit = (token, amount) => {
    return axios.post(`/api/users/deposit/?amount=${amount}`,
        {},
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
}

export const withdraw = (token, amount) => {
    return axios.post(`/api/users/deposit/?withdraw=${amount}`,
        {},
        {
            headers: {
                "Authorization": `Bearer ${token}`
            }
        })
}


// Items
export const getItems = (token) => {
    return axios.get("/api/items/", {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}

export const getLaidItems = (token) => {
    return axios.get("/api/items/laid", {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}

export const deleteItem = (token, item_id) => {
    return axios.delete(`/api/items/delete/${item_id}`, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}

export const createItem = (token, item) => {
    return axios.post("/api/items/create", item, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
}

export const transferItem = (token, transferItem) => {
    return axios.post("/api/items/transfer", transferItem, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
}

// Market
export const getOnSaleItems = (token) => {
    return axios.get("/api/market/", {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}


export const sellItem = (token, sellItem) => {
    return axios.post("/api/market/sell", sellItem, {
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        }
    })
}

export const buyItem = (token, item_id) => {
    return axios.post(`/api/market/buy/${item_id}`, {}, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}

export const rollbackToCollection = (token, item_id) => {
    return axios.delete(`/api/market/delete/${item_id}`, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}

export const updatePrice = (token, payableItem) => {
    return axios.post("/api/market/update", payableItem, {
        headers: {
            "Authorization": `Bearer ${token}`
        }
    })
}