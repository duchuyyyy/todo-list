import Login from "../pages/Login";
import Register from "../pages/Register";
import LayoutDefault from "../components/LayoutDefault/index"
import ForgotPassword from "../pages/ForgotPassword";
import PrivateRoutes from "../components/PrivateRoutes";

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
    }
]