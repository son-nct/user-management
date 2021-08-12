<%-- 
    Document   : createAccount
    Created on : Jun 5, 2021, 7:45:31 PM
    Author     : WIN 10
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User</title>

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
    </head>
    <body>
        <c:set var="user" value="${sessionScope.USER}" scope="session"/>
        <c:if test="${empty user}">
            <c:redirect url=""/>
        </c:if>

        <c:if test="${not empty user and user.role ne 'Admin'}">
            <c:redirect url="info.jsp"/>
        </c:if>
        
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
                    <li style="margin-right: 10px"><a href="manage.jsp" class="btn btn-info">Home</a></li>
                    <li><a href="logout"  class="btn btn-info">Logout</a></li>
                </ul>
            </header>
        </div>

        <div class="body-page">
            <div class="container col-xl-10 col-xxl-8 px-0 py-1" >
                <div class="row align-items-center g-lg-5 py-2">
                    <div class="col-md-10 mx-auto col-lg-5" >
                        <form action="createUser" method="POST" class="p-4 p-md-5 border rounded-3 bg-light" style="width: 400px">

                            <h3 style=" text-align: center; margin: -25px 0 20px 0">Create User</h3>

                            <c:set var="dto" value="${sessionScope.CREATE_USER}" scope="session"/>
                            <div class="form-floating mb-3">
                                <input name="txtUserID" value="${dto.username}" type="text" class="form-control" placeholder="UserID">
                                <label for="floatingInput">Username</label>

                                <c:set var="empty_username" value="${sessionScope.empty_username}" scope="session"/>
                                <c:set var="exist_username" value="${sessionScope.exist_username}" scope="session"/>

                                <c:choose>
                                    <c:when test="${not empty empty_username}">
                                        <p style=" color: red">${empty_username}</p>
                                        <c:remove var="empty_username"/>
                                    </c:when>

                                    <c:when test="${not empty exist_username}">
                                        <p style=" color: red">${exist_username}</p>
                                        <c:remove var="exist_username"/>
                                    </c:when>
                                </c:choose>

                            </div>

                            <div class="form-floating mb-3" style="position: relative">
                                <input name="txtPassword" value="${dto.password}" type="password" class="form-control"  placeholder="Password">
                                <label for="floatingPassword">Password</label>

                                <c:set var="empty_password" value="${sessionScope.empty_password}" scope="session"/>

                                <c:if test="${not empty empty_password}">
                                    <p style=" color: red">${empty_password}</p>
                                    <c:remove var="empty_password"/>
                                </c:if>

                            </div>

                            <div class="form-floating mb-3" style="position: relative">
                                <input name="txtRepassword" value="${sessionScope.REPASSWORD_VALUE}" type="password" class="form-control"  placeholder="Repassword">
                                <label for="floatingPassword">Repassword</label>

                                <c:set var="not_match" value="${sessionScope.not_match}" scope="session"/>
                                <c:if test="${not empty not_match}">
                                    <p style=" color: red">${not_match}</p>
                                    <c:remove var="not_match"/>
                                </c:if>
                            </div>

                            <div class="form-floating mb-3">
                                <input name="txtEmail" value="${dto.email}" type="text" class="form-control"   placeholder="name@example.com">
                                <label for="floatingInput">Email</label>

                                <c:set var="empty_email" value="${sessionScope.empty_email}" scope="session"/>
                                <c:set var="invalid_email" value="${sessionScope.invalid_email}" scope="session"/>

                                <c:choose>
                                    <c:when test="${not empty empty_email}">
                                        <p style=" color: red">${empty_email}</p>
                                        <c:remove var="empty_email"/>
                                    </c:when>

                                    <c:when test="${not empty invalid_email}">
                                        <p style=" color: red">${invalid_email}</p>
                                        <c:remove var="invalid_email"/>
                                    </c:when>
                                </c:choose>
                            </div>



                            <div class="form-floating mb-3">
                                <div id="overplay" class="position-fixed  start-0 end-0 bottom-0" style="top: 200%;background-color: rgba(255,255,255,0.5)"></div>
                                <div class="d-flex justify-content-center align-items-center position-fixed  start-0 end-0 bottom-0" id="loading" style="top:200%">
                                    <div class="spinner-border text-primary" role="status">
                                        <span class="visually-hidden">Loading...</span>
                                    </div>
                                </div>
                                <input name="txtPhone" value="${dto.phone}" type="text" class="form-control"   placeholder="phone">
                                <label for="floatingInput">Phone</label>

                                <c:set var="empty_phone" value="${sessionScope.empty_phone}" scope="session"/>
                                <c:set var="error_phone" value="${sessionScope.error_phone}" scope="session"/>

                                <c:choose>
                                    <c:when test="${not empty empty_phone}">
                                        <p style=" color: red">${empty_phone}</p>
                                        <c:remove var="empty_phone"/>
                                    </c:when>

                                    <c:when test="${not empty error_phone}">
                                        <p style=" color: red">${error_phone}</p>
                                        <c:remove var="error_phone"/>
                                    </c:when>
                                </c:choose>
                            </div>

                            <div class="form-floating mb-3">
                                <input name="txtFullname" value="${dto.fullname}" type="text" class="form-control"  placeholder="Fullname">
                                <label for="floatingInput">Fullname</label>

                                <c:set var="empty_fullname" value="${sessionScope.empty_fullname}" scope="session"/>

                                <c:if test="${not empty empty_fullname}">
                                    <p style=" color: red">${empty_fullname}</p>
                                </c:if>
                            </div>

                            <span class="row-input">
                                <label  class="labelLogin">Photo: </label> 
                                <input onchange="uploadImg()" id="file" type="file" name="txtPhoto" value="" placeholder="Please Choose Image...."/><br/>
                                <div class="img-container" style="text-align: center;">
                                    <img id="img" src="${dto.photo}" style="width: 55%; margin: 15px 0 20px -45%; object-fit: cover;"/>
                                    <input id="urlHidden" type="hidden" value="${dto.photo}" name="urlImg"/>
                                </div>
                            </span>

                            <c:set var="empty_img" value="${sessionScope.empty_img}" scope="session"/>
                            <c:if test="${not empty empty_img}">
                                <p style=" color: red">${empty_img}</p>
                                <c:remove var="empty_img"/>
                            </c:if>


                            <div class="form-floating mb-3">
                                <span class="row-input">
                                    <label  class="labelLogin">Role </label> 
                                    <select style="height: 30px; font-size: 14px" name="cboRole">
                                        <c:set var="listRole" value="${sessionScope.LIST_ROLE}" scope="session"/>
                                        <c:if test="${not empty listRole}">
                                            <c:forEach var="role" items="${listRole}">
                                                <option value="${role.idRole}"
                                                        <c:if test="${dto.role eq role.role}">
                                                            selected="true"
                                                        </c:if>
                                                        >${role.role}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </span>
                            </div>

                            <button  style="width: 100%; margin-top: 15px; font-size: 16px;" type="submit" class="btn btn-primary">Create User</button>

                        </form>
                    </div>
                </div>
            </div>
        </div>
        <script src="./assets/js/uploadImg.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </body>
</html>
