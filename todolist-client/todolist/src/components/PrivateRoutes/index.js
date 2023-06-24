import { Navigate } from "react-router-dom";
import LayoutDefault from "../LayoutDefault/index"

function PrivateRoutes() {
    const token = localStorage.getItem("ACCESS_TOKEN");
  
    return (
      <>
        {token ? (<LayoutDefault />) : (<Navigate to="/login" />)}
      </>
    )
  }
  
  export default PrivateRoutes;