<%-- 
    Document   : viewHistory
    Created on : Jun 16, 2021, 8:10:50 PM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View History Page</title>
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
                        <span  class="fs-4">History Promotion</span>
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
        <div class="container" >


            <c:if test="${not empty listUser}">
                <table class="table table-striped" style="font-size: 15px">
                    <thead>
                        <tr style="text-align: center">
                            <th class="font-weight-bold" scope="col">#</th>
                            <th class="font-weight-bold" scope="col">Photo</th>
                            <th class="font-weight-bold" scope="col">Username</th>
                            <th class="font-weight-bold" scope="col">Email</th>
                            <th class="font-weight-bold" scope="col">Phone</th>
                            <th class="font-weight-bold" scope="col">Fullname</th>
                            <th class="font-weight-bold" scope="col">Role</th>
                            <th class="font-weight-bold" scope="col" style="text-align: center">Rank</th>
                            <th class="font-weight-bold" scope="col">Date Assigned</th>
                        </tr>
                    </thead>
                    <tbody>

                        <c:set var="listHistory" value="${sessionScope.LIST_HISTORY}" scope="session"/>   

                        <c:forEach var="dto" items="${listHistory}" varStatus="counter">

                            <tr style="line-height: 86px; text-align: center">
                                <th class="font-weight-bold">${counter.count}</th>
                                <td>
                                    <img style="object-fit: cover;" width="80px" height="80px" src=" ${dto.photo}"/>
                                </td>
                                <td>
                                    ${dto.username}

                                </td>
                                <td>  ${dto.email}

                                </td>
                                <td>
                                    ${dto.phone}

                                </td>
                                <td>
                                    <p style="min-width: 168px">${dto.fullname}<p>
                                </td>
                                <td>
                                    ${dto.role}
                                </td>
                                <td>
                                    <div style="padding: 0 10px; display: flex; align-items: center; font-size: 17px">
                                        <i class="fas fa-medal text-warning" style="margin: -17px 5px 0 0"></i>
                                        <p class="text-danger font-weight-bold" style="color: #FF3D00">Top ${dto.rank}</p>
                                    </div>
                                </td>
                                <td>
                                    ${dto.dateAssign}
                                </td>
                            </tr>

                        </c:forEach>

                    </tbody>
                </table>
            </c:if>

        </div>
    </body>
</html>
