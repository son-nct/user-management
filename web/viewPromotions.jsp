<%-- 
    Document   : viewPromotions
    Created on : Jun 16, 2021, 11:22:41 AM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Promotion Page</title>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
        <!-- Font Awesome -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css"
            rel="stylesheet"
            />
        <!-- MDB -->
        <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.5.0/mdb.min.css"
            rel="stylesheet"
            />   
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>

    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}" scope="session"/>
        <c:if test="${empty user  }">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty user and user.role ne 'Admin'}">
            <c:redirect url="info.jsp"/>
        </c:if>
        
        <div class="header">
            <div class="container">
                <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
                    <a style="margin-top: -12px" href="manage.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
                        <i style="font-size: 18px;margin-right: 5px" class="far fa-user"></i>
                        <span  class="fs-4">User Management</span>
                    </a>

                    <div class="dropdown text-end" style="display: flex; margin-right: 15px; padding-top: 2px">
                        <a href="#" class="d-block link-dark text-decoration-none">
                            <img style="object-fit: cover;" src="${user.photo}" alt="mdo" width="32" height="32" class="rounded-circle">
                        </a>
                        <span style="margin: 5px">
                            <p>${user.fullname}</p>
                        </span>

                    </div>
                    <ul class="nav  justify-content-center">
                        <li style="margin-right: 10px"><a href="manage.jsp"  class="btn btn-info">Home</a></li>
                        <li><a href="logout" class="btn btn-info">Logout</a></li>
                    </ul>
                </header>
            </div>
        </div>
        <c:set var="map" value="${sessionScope.MAP}" scope="session"/>
        <div class="container" >

            <c:if test="${empty map}">
                <div class="d-flex flex-row text-danger justify-content-center">
                    <i  style="font-size: 1.4rem; margin: 4px 8px 0 0" class="fas fa-user-alt-slash"></i> <h3>Do not have any user !</h3>
                </div>

            </c:if>

            <c:if test="${not empty map}">
                <table class="table table-striped" style="font-size: 15px">
                    <thead>
                        <tr>
                            <th class="font-weight-bold" scope="col">#</th>
                            <th class="font-weight-bold" scope="col">Username</th>
                            <th class="font-weight-bold" scope="col">Email</th>
                            <th class="font-weight-bold" scope="col">Phone</th>
                            <th class="font-weight-bold" scope="col">Fullname</th>
                            <th class="font-weight-bold" scope="col">Role</th>
                            <th class="font-weight-bold" scope="col">Photo</th>
                            <th class="font-weight-bold" scope="col">Rank</th>
                            <th class="font-weight-bold" scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:forEach var="dto" items="${map.keySet()}" varStatus="counter">
                            <tr style="line-height: 86px">
                                <th class="font-weight-bold">${counter.count}</th>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername" value="${dto.username}"/>
                                </td>
                                <td>
                                    ${dto.email}
                                    <input type="hidden" name="txtEmail" value="${dto.email}"/>
                                </td>
                                <td>
                                    ${dto.phone}
                                    <input type="hidden" name="txtPhone" value="${dto.phone}"/>
                                </td>
                                <td>
                                    <p style="min-width: 168px">${dto.fullname}<p>
                                        <input type="hidden" name="txtFullname" value="${dto.fullname}"/>
                                </td>
                                <td>
                                    ${dto.role}
                                    <input type="hidden" name="txtRole" value="${dto.role}"/>
                                </td>

                                <td>
                                    <img style="object-fit: cover;" width="80px" height="80px" src=" ${dto.photo}"/>
                                    <input type="hidden" name="urlImg" value="${dto.photo}"/>
                                </td>

                                <td style="text-align: center">${map.get(dto)}</td>


                                <td>
                                    <div style="display: flex; align-items: center; margin-top: 10%; min-width: 250px">
                                        <button onclick="askToDelete('${dto.username}')" style="margin-right: 15px" id="btn-delete" type="button"  type="button" class="btn btn-danger">Delete</button>

                                        <button onclick="updateRank('${dto.username}')"  type="button" class="btn btn-success">Update Rank</button>
                                    </div>

                                </td>
                            </tr>

                        </c:forEach>
                    <button onclick="askToVerify()" type="button" class="btn btn-warning" style="float: right; margin-bottom: 15px">Confirm</button>
                    </tbody>
                </table>

            </c:if>
        </div>
        <script>
            function updateRank(username) {
                var btnAction = 'update';
                swal({
                    title: "Enter new rank ",
                    content: {
                        element: "input",
                        attributes: {
                            placeholder: "Type positive number from 1 to 10",
                            type: "number",
                        },
                    },
                    buttons: true,
                    dangerMode: true
                })
                        .then((value) => {
                            if (value.length > 0 && parseInt(value) > 0 && parseInt(value) < 11) {
                                window.location.href = "processPromotion?txtUsername=" + username
                                        + "&txtRank=" + value
                                        + "&btnAction=" + btnAction;
                            } else {
                                swal(`Please input positive number from 1 to 10!`);
                            }

                        });
            }

            function askToDelete(username) {
                var btnAction = 'delete';

                swal({
                    title: "Are you sure to delete this user?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true
                })
                        .then((willDelete) => {
                            if (willDelete) {
                                window.location.href = "processPromotion?txtUsername=" + username
                                        + "&btnAction=" + btnAction;

                            }
                        });
            }

            function askToVerify(username) {
                var btnAction = 'delete';

                swal({
                    title: "Are you sure to update the promotions?",
                    icon: "warning",
                    buttons: true,
                    dangerMode: true
                })
                        .then((willUpdate) => {
                            if (willUpdate) {
                                window.location.href = "updatePromotion";
                            }
                        });
            }
        </script>
    </body>
</html>

