/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

async function converToSHA256(message) {
    const msgUint8 = new TextEncoder().encode(message);
    const hashBuffer = await crypto.subtle.digest('SHA-256', msgUint8);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
    return hashHex;
}


function askToDelete(username) {
    var curPassword = document.getElementById('curPassword').value;
    swal({
        title: "Are you sure to delete ?\n\
                            If you delete, you couldn't access this account anymore !",
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
                                        window.location.href = "DeleteUserServlet?username=" + username;
                                    }
                                });
                            });
                }
            });
}