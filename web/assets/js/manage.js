/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


async function converToSHA256(message) {
    const msgUint8 = new TextEncoder().encode(message);                           // encode as (utf-8) Uint8Array
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgUint8);           // hash the message
    const hashArray = Array.from(new Uint8Array(hashBuffer));                     // convert buffer to byte array
    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join(''); // convert bytes to hex string
    return hashHex;
}


function askToDelete(username) {
    var searchValue = document.getElementById('searchValue').value;
    var btnRole = document.getElementById('btnRole').value;
    var curPassword = document.getElementById('curPassword').value;
    swal({
        title: "Are you sure to delete this user?",
        icon: "warning",
        buttons: true,
        dangerMode: true
    })
            .then((willDelete) => {
                if (willDelete) {

                    swal({
                        title: "Input your password to confirm !",
                        content: {
                            element: "input",
                            attributes: {
                                placeholder: "Enter password...",
                                type: "password"
                            }
                        },
                        buttons: true,
                        dangerMode: true
                    })
                            .then((value) => {
                                var hashCode = converToSHA256(value.toString());
                                hashCode.then((passwordEncoded) => {
                                    if (passwordEncoded !== curPassword) {
                                        swal("Password is not correct!", "", "error");
                                    } else {
                                        window.location.href = "DeleteUserServlet?username=" + username
                                                + "&searchValue=" + searchValue + "&btnRole=" + btnRole;
                                    }
                                });
                            });

                }
            });
}

function addToPromotionList(username, email, phone, fullname, role, photo) {
    window.location.href = "addPromotion?username=" + username
            + "&email=" + email + "&fullname=" + fullname
            + "&role=" + role + "&photo=" + photo + "&phone=" + phone;
}
