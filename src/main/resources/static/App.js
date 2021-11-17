document.getElementById("ingresar")
    .addEventListener("click", (e) => {
        console.log('vamos bien')
        e.preventDefault()
        // let email = document.getElementById("floatingInput").value
        let password = document.getElementById("floatingPassword").value
        let username = document.getElementById("username").value
        const data = {username, password}

        console.log(JSON.stringify(data))

        fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {'Content-Type': 'application/json', 'Accept': '*/*'},
            body:
                JSON.stringify(data)
        })

            .then((data) => {
                // data.headers.forEach((value) => console.log(value))

                // console.log(data.headers["Authorization"]);
                localStorage.setItem("Authorization", data.headers.get("Authorization"))

                // window.location.href = "http://localhost:8080/management/user"


            })
            .catch((err) => console.log(err.message))
    })


document.getElementById("users")
    .addEventListener("click", (e) => {


        fetch('http://localhost:8080/management/user', {
            headers: {
                'Content-Type': 'application/json',
                'Accept': '*/*',
                'Authorization': localStorage.getItem("Authorization")
            }
        })
            .then((data) => data.json())
            .then(dat => caca(dat))
            .then(datos => console.log(datos))
            .catch((err) => console.log(err.message))
    })


function caca(hola){
   document.getElementById("tabla").innerHTML = hola[0].username + hola[0].password;
}
