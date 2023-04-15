/* Making all APIs ready */

let user=""
let jwt=localStorage.getItem("jwt") || ""

/* Register an user */
let userRegistration = async () => {
    let options = {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
                "userName": "Aman",
                "email": "patro.aman@gmail.com",
                "password": "uttam@1997",
                "address": "Odisha",
                "role": "user"
        })
    }

    let p = await fetch("http://localhost:8844/taskplanner/user/register", options)
    let response = await p.json()
    console.log(response);
} 

// userRegistration();

/* Login user */
let userLogin = async () => {
    let options ={
        method: "GET",
        headers: {
            'Authorization': `Basic ${btoa(`${"patro.uttam@gmail.com"}:${"uttam@1997"}`)}`
        }
    }

    let p = await fetch("http://localhost:8844/taskplanner/user/login", options)
    if(p.ok){
        let jwt=p.headers.get('Authorization')
        console.log("jwt: "+jwt);
        localStorage.setItem("jwt",jwt)
        let response = await p.json()
        console.log(response);
    }
}
// userLogin();

/* Logout user */
let logout = async () => {

    let options = {
        method:'GET',
        headers:{
        "Authorization":`Bearer ${jwt}`
        }
    }

    let p = await fetch("http://localhost:8844/logout", options)
    if(p.ok){
        // let response = await p.json()
        // console.log(response)
        // user=""
        // jwt=""
        localStorage.removeItem("jwt")
        console.log("Logged out...");
    }
}
// logout();

/* Create a task */
let createTask = async () => {

    let obj = {
        "taskDesc": "Needed to fix the add-product-to-cart API.",
        "startDate": "2023-05-03",
        "endDate": "2023-07-04",
        "type": "BUG",
        "priority": "HIGH"
    }

    let options = {
        method: "POST",
        body:JSON.stringify(obj),
        headers:{
            'Content-Type':'application/json',
            "Authorization":`Bearer ${jwt}`
          }
    }

    let p = await fetch("http://localhost:8844/taskplanner/task/create", options)
    if(p.ok){
        let response = await p.json()
        console.log(response);
    }
}
// createTask();



