<%-- 
    Document   : manage
    Created on : Jun 5, 2021, 10:03:50 AM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Page</title>
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
        <c:if test="${empty user}">
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
                            <input type="hidden" value="${user.password}" id="curPassword"/>
                        </span>

                    </div>
                    <ul class="nav  justify-content-center">
                        <li style="margin-right: 10px"><a href="viewHistory"  class="btn btn-info">history of promotions</a></li>
                        <li style="margin-right: 10px"><a href="viewListPromotion"  class="btn btn-info">Promotion List</a></li>
                        <li style="margin-right: 10px"><a href="createAccount.jsp"  class="btn btn-info">Create User</a></li>
                        <li><a href="logout" class="btn btn-info">Logout</a></li>
                    </ul>
                </header>
            </div>
        </div>
        <c:set var="tabRole" value="${sessionScope.TAB_ROLE}" scope="session"/>
        <div class="container" >
            <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start" style="float: right; margin: -10px 0 10px 0">
                <form action="changeTabUser" class="col-12 col-lg-auto mb-3 mb-lg-0 me-lg-3">
                    <div style="display: flex">
                        <input id="searchValue" name="txtSearch" value="${sessionScope.SEARCH_VALUE}" style="margin: 0 15px" type="search" class="form-control" placeholder="Search..." aria-label="Search">
                        <input id="btnRole" type="hidden" name="btnRole" value="${tabRole}"/>
                        <button class="btn btn-primary">Search</button>
                    </div>
                </form>
            </div>

            <c:set var="listRole" value="${sessionScope.LIST_ROLE}" scope="session"/>
            <form action="changeTabUser">
                <ul class="nav nav-tabs">
                    <c:if test="${tabRole eq 'All'}">
                        <li class="nav-item">
                            <button name="btnRole" value="" class="nav-link active" type="button">All</button>
                        </li>
                    </c:if>
                    <c:if test="${tabRole ne 'All'}">
                        <li class="nav-item">
                            <button name="btnRole" value="" class="nav-link" type="submit">All</button>
                        </li>
                    </c:if>

                    <c:if test="${not empty listRole}">
                        <c:forEach var="role" items="${listRole}">
                            <c:if test="${tabRole eq role.idRole}">
                                <li class="nav-item">
                                    <button name="btnRole" value="${role.idRole}" type="button" class="nav-link active">${role.role}</button>
                                </li>
                            </c:if>
                            <c:if test="${tabRole ne role.idRole}">
                                <li class="nav-item">
                                    <button name="btnRole" value="${role.idRole}" type="submit" class="nav-link">${role.role}</button>
                                </li>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </ul>
            </form>

            <c:set var="listUser" value="${sessionScope.LIST_USER}" scope="session"/>
            <c:if test="${not empty listUser}">
                <table class="table table-striped" style="font-size: 15px">
                    <thead>
                        <tr>
                            <th class="font-weight-bold" scope="col">#</th>
                            <th class="font-weight-bold" scope="col">Username</th>
                            <th class="font-weight-bold" scope="col">Email</th>
                            <th class="font-weight-bold" scope="col">Phone</th>
                            <th class="font-weight-bold" scope="col">Fullname</th>
                            <th class="font-weight-bold" scope="col">Role</th>
                            <th class="font-weight-bold" scope="col" style="text-align: center">Rank</th>
                            <th class="font-weight-bold" scope="col">Photo</th>
                            <th class="font-weight-bold" scope="col">Action</th>
                        </tr>
                    </thead>
                    <tbody>


                        <c:set var="listRank" value="${sessionScope.LIST_RANK}" scope="session"/>
                        <c:set var="checkRank" value=""/>
                        <c:set var="rank" value=""/>

                        <c:set var="listUserAdding" value="${sessionScope.LIST_USER_ADDING}" scope="session"/>
                        <c:set var="checkAddUser" value=""/>

                        <c:forEach var="dto" items="${listUser}" varStatus="counter">

                            <c:forEach var="promotion" items="${listRank}">
                                <c:if test="${dto.username eq promotion.username}">
                                    <c:set var="checkRank" value="${promotion.username}"/>
                                    <c:set var="rank" value="${promotion.rank}"/>
                                </c:if>
                            </c:forEach>


                            <c:forEach var="addedUser" items="${listUserAdding}">
                                <c:if test="${dto.username eq addedUser}">
                                    <c:set var="checkAddUser" value="${addedUser}"/>
                                </c:if>
                            </c:forEach>


                        <form action="loadInfoUpdate" method="POST">
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
                                <td style="text-align: center">
                                    <div style="margin-top: -22%; min-width: 110%">
                                        <c:if test="${dto.username ne user.username and dto.role ne 'Admin'}">

                                            <c:if test="${dto.username ne checkRank}">

                                                <c:if test="${checkAddUser ne dto.username}">
                                                    <button onclick="addToPromotionList('${dto.username}', '${dto.email}', ' ${dto.phone}', '${dto.fullname}', '${dto.role}', '${dto.photo}')" 
                                                            style="min-width: 110px" type="button" class="btn btn-secondary">
                                                        Add Rank
                                                    </button>
                                                </c:if>

                                                <c:if test="${checkAddUser eq dto.username}">
                                                    <div style="margin-top: 22%">
                                                        <i class="fas fa-hourglass-half"></i> Processing
                                                    </div>

                                                </c:if>
                                            </c:if>

                                            <c:if  test="${dto.username eq checkRank}">
                                                <div style="min-width: 80px ; margin-top: 22%; padding: 0 10px; display: flex; align-items: center; font-size: 17px">
                                                    <i class="fas fa-medal text-warning" style="margin: -17px 5px 0 0"></i>
                                                    <p class="text-danger font-weight-bold" style="color: #FF3D00">Top ${rank}</p>
                                                </div>
                                            </c:if>

                                        </c:if>
                                    </div>

                                </td>
                                <td>
                                    <img style="object-fit: cover;" width="80px" height="80px" src=" ${dto.photo}"/>
                                    <input type="hidden" name="urlImg" value="${dto.photo}"/>
                                </td>
                                <td>
                                    <c:if test="${user.username eq dto.username}">
                                        <div  style="margin-top: -11%; display: flex; align-items: center; margin-top: 13%">
                                            <button style="width: 90px; margin-right: 15px" type="button" onclick="askToDelete('${dto.username}')" type="submit" class="btn btn-danger">Delete</button>
                                            <button type="submit" class="btn btn-success">Update</button>
                                        </div>
                                    </c:if>

                                    <div style="display: flex; align-items: center; margin-top: 13%">
                                        <c:if test="${dto.role ne 'Admin'}">
                                            <button style="width: 90px; margin-right: 15px" type="button" onclick="askToDelete('${dto.username}')" type="submit" class="btn btn-danger">Delete</button>
                                            <button style="width: 90px" type="submit" class="btn btn-success">Update</button>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </form>

                    </c:forEach>

                    </tbody>
                </table>
            </c:if>

        </div>
        <script src="./assets/js/manage.js"></script>
    </body>
</html>
