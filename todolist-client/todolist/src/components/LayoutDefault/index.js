import { Layout, theme, Modal } from "antd";
import {
  WarningOutlined,
  InfoCircleOutlined,
  CheckOutlined,
  UserOutlined,
  LogoutOutlined,
} from "@ant-design/icons";
import { Menu } from "antd";
import "./LayoutDefault.scss";
import { useEffect, useState } from "react";
import {
  createTodo,
  deleteTodo,
  getAllTodo,
  markDoneTodo,
  markImportantTodo,
  updateTodo,
} from "../../services/TodoService";
import { clearDataStorage } from "../../helpers/localStorageService";
import { useNavigate } from "react-router";

const { Header, Content, Footer } = Layout;

const items = [
  {
    label: "All",
    key: "all",
    icon: <InfoCircleOutlined />,
  },
  {
    label: "Important",
    key: "important",
    icon: <WarningOutlined />,
  },
  {
    label: "Done",
    key: "done",
    icon: <CheckOutlined />,
  },
];

const LayoutDefault = () => {
  const navigate = useNavigate();
  const [current, setCurrent] = useState("mail");
  const [todos, setTodo] = useState([]);
  const [addTodo, setAddTodo] = useState();
  const [status, setStatus] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [taskId, setTaskId] = useState();
  const [updateValueTodo, setUpdateValueTodo] = useState();

  const showModal = (id) => {
    setIsModalOpen(true);
    setTaskId(id);
  };
  const handleModalOk = async () => {
    if(updateValueTodo !== undefined) {
      const result = await updateTodo(taskId, updateValueTodo);
      setIsModalOpen(false);
      setStatus(!status);
      return result;
    }
  };
  const handleModalCancel = () => {
    console.log(updateValueTodo);
    setIsModalOpen(false);
  };

  useEffect(() => {
    const fetchTodo = async () => {
      const id = window.localStorage.getItem("ID_USER");
      const result = await getAllTodo(id);
      setTodo(result);
    };
    const handleBlockBack = () => {
      window.history.forward();
    };

    window.history.pushState(null, null, window.location.pathname);
    window.addEventListener('popstate', handleBlockBack);
    fetchTodo();
  }, [status]);

  const onClick = (e) => {
    console.log("click ", e);
    setCurrent(e.key);
  };

  const handleLogout = () => {
    clearDataStorage();
    navigate("/login");
  };

  const handleAddTodo = async () => {
    const data = {
      task: addTodo,
    };
    const result = await createTodo(data);
    console.log(result);
  };

  const handleDeleteTodo = async (idTodo) => {
    const result = await deleteTodo(idTodo);
    setStatus(!status);
    console.log(result);
  };

  const handleImportantTodo = async (idTodo) => {
    const result = await markImportantTodo(idTodo);
    setStatus(!status);
    console.log(result);
  };

  const handleDoneTodo = async (idTodo) => {
    const result = await markDoneTodo(idTodo);
    setStatus(!status);
    console.log(result);
  };

  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <>
      <Layout>
        <Header
          style={{
            position: "sticky",
            top: 0,
            zIndex: 1,
            width: "100%",
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
          }}
        >
          <div className="header__content">
            <div className="header__logo">
              <UserOutlined />
            </div>
            <div className="header__text">
              <h4>Username</h4>
            </div>
          </div>
          <div className="header__logout">
            <LogoutOutlined />
            <button className="header__button" onClick={handleLogout}>
              Logout
            </button>
          </div>
        </Header>
        <Content
          className="site-layout"
          style={{
            padding: "0 50px",
            paddingBottom: "100px",
          }}
        >
          <div
            style={{
              padding: 100,
              paddingBottom: 600,
              minHeight: 380,
              background: colorBgContainer,
            }}
            className="content"
          >
            <form className="form">
              <div className="content__form">
                <div>
                  <input
                    type="text"
                    id="form2"
                    className="content__input"
                    placeholder="New task..."
                    required
                    onChange={(e) => setAddTodo(e.target.value)}
                  />
                  <label className="form-label" htmlFor="form2"></label>
                </div>
                <button
                  type="submit"
                  className="content__button"
                  onClick={handleAddTodo}
                >
                  Add
                </button>
              </div>
            </form>

            <Menu
              className="content__menu"
              onClick={onClick}
              selectedKeys={[current]}
              mode="horizontal"
              items={items}
              defaultSelectedKeys={["all"]}
            />

            <div>
              {Array.isArray(todos.data) && todos.data.length > 0 ? (
                todos.data.map((data) => (
                  <li key={data.id} className="todo">
                    <span className="todo__label">{data.task + "  "}</span>
                    <div className="todo__button">
                      <button
                        className="todo__button--update"
                        onClick={() => showModal(data.id)}
                      >
                        Update
                      </button>
                      <Modal
                        title="Update task"
                        open={isModalOpen}
                        onOk={handleModalOk}
                        onCancel={handleModalCancel}
                      >
                        <input required type="text" className="modal-input" onChange={(e) => setUpdateValueTodo(e.target.value)} />
                      </Modal>
                      <button
                        className="todo__button--delete"
                        onClick={() => handleDeleteTodo(data.id)}
                      >
                        Delete
                      </button>
                      <button
                        className="todo__button--important"
                        onClick={() => handleImportantTodo(data.id)}
                      >
                        Important
                      </button>
                      <button
                        className="todo__button--done"
                        onClick={() => handleDoneTodo(data.id)}
                      >
                        Done
                      </button>
                    </div>
                  </li>
                ))
              ) : (
                <p>No todos found.</p>
              )}
            </div>
          </div>
        </Content>
        <Footer
          style={{
            textAlign: "center",
          }}
        >
          ToDo Web 2023
        </Footer>
      </Layout>
    </>
  );
};
export default LayoutDefault;
