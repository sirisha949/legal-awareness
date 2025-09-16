function navigateHome() {
    window.location.href = 'home.html';
}
function navigateAbout() {
    window.location.href = 'about.html';
}
function navigateBlog() {
    window.location.href = 'blog.html';
}
function navigateLogin() {
    window.location.href = 'login.html';
}

var acc = document.getElementsByClassName("accordion");
for (var i = 0; i < acc.length; i++) {
    acc[i].addEventListener("click", function() {
        var panel = this.nextElementSibling;
        if (panel.style.display === "block") {
            panel.style.display = "none";
        } else {
            panel.style.display = "block";
        }
    });
}

function sendScoreToServer(score) {
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "SaveScoreServlet", true); // Assuming you will create saveScoreServlet
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log("Score saved successfully");
        }
    };
    xhr.send("score=" + score);
}

class SpecialHeader extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
            <div class="container-fluid sticky-top">
                <div class="row">
                    <div class="col-auto"><img src="logo.png" alt="logo" id="logo"></div>
                    <div class="col-7" id="title">LEGAL AWARENESS</div>
                    <div class="col-1"><button class="btn" onclick="navigateHome()"><b>HOME</b></button></div>
                    <div class="col-1"><button class="btn" onclick="navigateAbout()"><b>ABOUT</b></button></div>
                    <div class="col-1"><button class="btn" onclick="navigateBlog()"><b>BLOG</b></button></div>
                    <!--<div class="col-1"><button class="btn" onclick="navigateLogin()"><b>LOGOUT</b></button></div>-->
                    <div class="col-1">
                        <div class="dropdown">
                            <button class="btn" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <img src="userlogo.png" alt="user" style="width: 50%;height: 50%;">
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                <p id="user-email">user@example.com</p>
                                <p id="user-score">Highest Score: 100</p>
                                <a class="dropdown-item" href="login.html">LOGOUT</a>
                            </div>
                        </div>
                    </div>      
                </div>
            </div>
        `;

        // Fetch user data and update the header
        this.fetchUserData();
    }

    fetchUserData() {
        fetch('UserDataServlet')
            .then(response => response.json())
            .then(data => {
                if (data.error) {
                    console.error(data.error);
                } else {
                    document.getElementById('user-email').textContent = data.email;
                    document.getElementById('user-score').textContent = `Highest Score: ${data.score}`;
                }
            })
            .catch(error => console.error('Error fetching user data:', error));
    }
}

customElements.define('special-header', SpecialHeader);