import Login from "../pages/Login";
import Register from "../pages/Register";
import LayoutDefault from "../components/LayoutDefault/index"
import ForgotPassword from "../pages/ForgotPassword";
import PrivateRoutes from "../components/PrivateRoutes";
import NotFound from "../pages/NotFound";
import ForgotPasswordRequestSuccess from "../pages/ForgotPassword/ForgotPasswordRequestSuccess";
import ResetPassword from "../pages/ResetPassword";
import ResetPasswordSuccess from "../pages/ResetPassword/ResetPasswordSuccess"

export const routes = [
    {
        path: "/login",
        element: <Login />
    },
    {
        path: "/register",
        element: <Register />
    },
    {
        element: <PrivateRoutes />,
        children: [
            {
                path: "/",
                element: <LayoutDefault />
            }
        ]
    },
    {
        path:"/forgot-password",
        element: <ForgotPassword />
    },
    {
        path: "/reset-password/:resetpasswordtoken",
        element: <ResetPassword />
    },
    {
        path: "reset-password/success",
        element: <ResetPasswordSuccess />
    },
    {
        path:"/not-found",
        element: <NotFound />
    },
    {
        path:"/forgot-password/success",
        element: <ForgotPasswordRequestSuccess />
    }
]