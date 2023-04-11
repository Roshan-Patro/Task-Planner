/* Making all APIs ready */

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
        let response = await p.json()
        console.log(response);
    }
}
userLogin();

/* Create a task */
