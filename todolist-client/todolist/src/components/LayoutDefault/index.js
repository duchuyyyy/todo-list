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
  undoImportantTodo,
  updateTodo,
} from "../../services/TodoService";
import { clearDataStorage } from "../../helpers/localStorageService";
import { useNavigate } from "react-router";
import { getInfoUser } from "../../services/UserService";

const { Header, Content, Footer } = Layout;

const items = [
  {
    label: "Task",
    key: "task",
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
  const [current, setCurrent] = useState("task");
  const [todos, setTodo] = useState([]);
  const [addTodo, setAddTodo] = useState();
  const [status, setStatus] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [taskId, setTaskId] = useState();
  const [updateValueTodo, setUpdateValueTodo] = useState();
  const [email, setEmail] = useState();

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

    const fetchInfo = async () => {
      const result = await getInfoUser();
      if(result.status === 200) {
        setEmail(result.data);
      }
    }

    const handleBlockBack = () => {
      window.history.forward();
    };

    window.history.pushState(null, null, window.location.pathname);
    window.addEventListener('popstate', handleBlockBack);

    fetchTodo();
    fetchInfo();
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

  const markUndoImportantTodo = async (idTodo) => {
    const result = await undoImportantTodo(idTodo);
    setStatus(!status);
    return result;
  }

  const handleDoneTodo = async (idTodo) => {
    const result = await markDoneTodo(idTodo);
    setStatus(!status);
    console.log(result);
  };

  const {
    token: { colorBgContainer },
  } = theme.useToken();

  const filterTodos = () => {
    if (todos && Array.isArray(todos.data) && todos.data.length > 0) {
      if (current === 'important') {
        return todos.data.filter((todo) => todo.important === true && todo.status !== true);
      } else if (current === 'done') {
        return todos.data.filter((todo) => todo.status === true);
      } else if (current === 'task'){
        return todos.data.filter((todo) => todo.status === false);
      }
  }
};

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
              <h4>{email}</h4>
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
            />

            <div>
              {todos && Array.isArray(todos.data) && todos.data.length > 0 ? (
               filterTodos().map((data) => {
                const todoClass = `todo ${data.status ? 'todo--completed' : ''} ${data.important ? 'todo--important' : ''}`;
                
                return (
                <li key={data.id} className={todoClass}>
                  <span className={`todo__label ${data.status ? 'todo__label--completed' : ''}`}>
                    {data.task + "  "}
                  </span>
                  {!data.status && (
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
                        <input
                          required
                          type="text"
                          className="modal-input"
                          onChange={(e) => setUpdateValueTodo(e.target.value)}
                        />
                      </Modal>
                      <button
                        className="todo__button--delete"
                        onClick={() => handleDeleteTodo(data.id)}
                      >
                        Delete
                      </button>
                      {data.important ? 
                      <button
                        className="todo__button--important"
                        onClick={() => markUndoImportantTodo(data.id)}
                      >
                        Unimportant
                      </button>
                      :
                      <button
                      className="todo__button--important"
                      onClick={() => handleImportantTodo(data.id)}
                    >
                      Important
                    </button>
                      }
                      <button
                        className={`todo__button--${data.done ? 'undone' : 'done'}`}
                        onClick={() => handleDoneTodo(data.id)}
                      >
                        Done
                      </button>
                    </div>
                  )}
                </li>
              )})
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
