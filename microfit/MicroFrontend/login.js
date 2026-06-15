function login() {

    const email =
        document.getElementById("email").value;

    const password =
        document.getElementById("password").value;

    if (!email || !password) {

        document.getElementById("message")
            .innerText =
            "Please enter email and password";

        return;
    }

  fetch(
      "https://whoopee-browbeat-unpack.ngrok-free.dev/api/auth/login",
        {
            method: "POST",

            headers: {
                "Content-Type":
                    "application/json"
            },

            body: JSON.stringify({

                email: email,
                password: password
            })
        }
    )

    .then(response => {

        if (!response.ok) {

            throw new Error(
                "Invalid Credentials"
            );
        }

        return response.text();
    })

    .then(token => {

        localStorage.setItem(
            "token",
            token
        );

        window.location.href =
            "dashboard.html";
    })

    .catch(error => {

        document.getElementById("message")
            .innerText =
            "Invalid Email or Password";
    });
}