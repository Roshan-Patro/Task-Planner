/* Making all APIs ready */

/* Register an user */
let userRegistration = async () => {
    let options = {
        method: "POST",
        headers: {
            "Content-type": "application/json"
        },
        body: JSON.stringify({
                "userName": "Alok",
                "email": "patro.alok@gmail.com",
                "password": "uttam@1997",
                "address": "Odisha",
                "role": "user"
        })
    }

    let p = await fetch("http://localhost:8844/taskplanner/user/register", options);
    let response = await p.json();
    console.log(response);
} 

userRegistration();