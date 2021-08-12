<%-- 
    Document   : employee
    Created on : Jun 7, 2021, 7:54:27 PM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
        <title>Employee Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}" scope="session"/>
        <c:if test="${empty user}">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty user and user.role eq 'Admin'}">
            <c:redirect url="manage.jsp"/>
        </c:if>

        <div class="header">
            <div class="container">
                <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
                    <a style="margin-top: -12px" href="info.jsp" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
                        <i style="font-size: 18px;margin-right: 5px" class="far fa-user"></i>
                        <span  class="fs-4">Your information</span>
                        <input type="hidden" value="${user.password}" id="curPassword"/>
                    </a>

                    <div class="dropdown text-end" style="display: flex; margin-right: 15px; padding-top: 2px">
                        <a href="#" class="d-block link-dark text-decoration-none">
                            <img style="object-fit: cover;"  src="${user.photo}" alt="mdo" width="32" height="32" class="rounded-circle">
                        </a>
                        <span style="margin: 5px">
                            <p>${user.fullname}</p>
                        </span>

                    </div>
                    <ul class="nav  justify-content-center">
                        <li><a href="logout"  class="btn btn-info">Logout</a></li>
                    </ul>
                </header>
            </div>
        </div>
        <div style="display: flex">
            <div style="margin: auto">
                <form action="loadInfoUpdate">
                    <div class="card" style="width: 20rem; margin: 2% auto">
                        <img src="${user.photo}" class="card-img-top" alt="...">
                        <input type="hidden" name="urlImg" value="${user.photo}"/>

                        <div class="card-body" style="font-size: 18px">
                            <input type="hidden" name="txtUsername" value="${user.username}"/>

                            <span style="display: flex">
                                <label style="min-width: 70px"><strong>Name:</p></strong></label>
                                <p>${user.fullname}</p>
                                <input type="hidden" name="txtFullname" value="${user.fullname}"/>
                            </span>
                            <span style="display: flex">
                                <label style="min-width: 70px"><strong>Email:</p></strong></label>
                                <p>${user.email}</p>
                                <input type="hidden" name="txtEmail" value="${user.email}"/>
                            </span>
                            <span style="display: flex">
                                <label style="min-width: 70px"><strong>Phone:</p></strong></label>
                                <p>${user.phone}</p>
                                <input type="hidden" name="txtPhone" value="${user.phone}"/>
                            </span>
                            <span style="display: flex">
                                <label style="min-width: 70px"><strong>Role:</p></strong></label>
                                <p>${user.role}</p>
                                <input type="hidden" name="txtRole" value="${user.role}"/>
                            </span>
                            <c:set var="rank" value="${sessionScope.RANK}" scope="session"/>
                            <span style="display: flex">
                                <label style="min-width: 70px"><strong>Rank:</p></strong></label>
                                <c:if test="${not empty rank}">
                                    <div style="min-width: 100px ;  display: flex; align-items: center; font-size: 17px">
                                        <i class="fas fa-medal text-warning" style="margin: -17px 5px 0 0"></i>
                                        <p class="text-danger font-weight-bold" style="color: #FF3D00">Top ${rank}</p>
                                    </div>
                                </c:if>

                                <c:if test="${empty rank}">
                                    No Rank
                                </c:if>

                            </span>
                            <td>
                                <div style="display: flex; align-items: center; margin-top: 4%">
                                    <button id="btn-delete" type="button" onclick="askToDelete('${user.username}')" style="margin-right: 15px" class="btn btn-danger">Delete</button>
                                    <button type="submit" class="btn btn-success">Update</button>
                                </div>
                            </td>
                        </div>
                    </div>
                </form>
            </div>

        </div>
        <script src="./assets/js/info.js"> </script>
    </body>
</html>
