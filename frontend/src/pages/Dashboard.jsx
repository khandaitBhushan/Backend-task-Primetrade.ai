import { useState, useEffect, useContext } from 'react';
import api from '../services/api';
import { AuthContext } from '../context/AuthContext';

export default function Dashboard() {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [status, setStatus] = useState('PENDING');
  const [editId, setEditId] = useState(null);
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const { user } = useContext(AuthContext);

  const fetchTasks = async () => {
    try {
      const response = await api.get('/tasks');
      setTasks(response.data);
    } catch (err) {
      setError('Failed to fetch tasks');
    }
  };

  useEffect(() => {
    fetchTasks();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editId) {
        await api.put(`/tasks/${editId}`, { title, description, status });
        setMessage('Task updated successfully');
      } else {
        await api.post('/tasks', { title, description, status });
        setMessage('Task created successfully');
      }
      setTitle('');
      setDescription('');
      setStatus('PENDING');
      setEditId(null);
      fetchTasks();
      setTimeout(() => setMessage(''), 3000);
    } catch (err) {
      setError('Operation failed');
    }
  };

  const handleDelete = async (id) => {
    try {
      await api.delete(`/tasks/${id}`);
      fetchTasks();
      setMessage('Task deleted');
      setTimeout(() => setMessage(''), 3000);
    } catch (err) {
      setError('Failed to delete task');
    }
  };

  const handleEdit = (task) => {
    setTitle(task.title);
    setDescription(task.description);
    setStatus(task.status);
    setEditId(task.id);
  };

  return (
    <div className="w-full max-w-4xl mx-auto">
      <div className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4">
        <h2 className="text-2xl font-bold mb-4">Welcome, {user?.username} ({user?.role})</h2>
        {error && <div className="bg-red-100 text-red-700 p-2 mb-4 rounded">{error}</div>}
        {message && <div className="bg-green-100 text-green-700 p-2 mb-4 rounded">{message}</div>}
        
        <form onSubmit={handleSubmit} className="mb-8 p-4 border rounded bg-gray-50">
          <h3 className="text-xl mb-4">{editId ? 'Edit Task' : 'Create Task'}</h3>
          <div className="grid grid-cols-1 gap-4 mb-4">
            <input
              type="text"
              placeholder="Task Title"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              required
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 list-none focus:outline-none"
            />
            <textarea
              placeholder="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 focus:outline-none"
            />
            <select
              value={status}
              onChange={(e) => setStatus(e.target.value)}
              className="shadow border rounded w-full py-2 px-3 text-gray-700 focus:outline-none"
            >
              <option value="PENDING">Pending</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>
          </div>
          <button type="submit" className="bg-blue-500 text-white px-4 py-2 rounded font-bold">
            {editId ? 'Update' : 'Create'}
          </button>
          {editId && (
            <button type="button" onClick={() => { setEditId(null); setTitle(''); setDescription(''); }} className="ml-2 bg-gray-500 text-white px-4 py-2 rounded font-bold">
              Cancel
            </button>
          )}
        </form>

        <div>
           <h3 className="text-xl mb-4">Your Tasks</h3>
           <div className="grid gap-4">
             {tasks.map(task => (
               <div key={task.id} className="border p-4 rounded bg-white shadow flex justify-between items-center">
                 <div>
                   <h4 className="font-bold text-lg">{task.title}</h4>
                   <p className="text-gray-600">{task.description}</p>
                   <span className={`inline-block px-2 py-1 mt-2 text-sm font-semibold rounded shadow-sm ${
                     task.status === 'COMPLETED' ? 'bg-green-100 text-green-800' : 
                     task.status === 'IN_PROGRESS' ? 'bg-yellow-100 text-yellow-800' : 'bg-gray-100 text-gray-800'
                   }`}>
                     {task.status}
                   </span>
                 </div>
                 <div>
                   <button onClick={() => handleEdit(task)} className="bg-yellow-500 text-white font-bold shadow px-3 py-1 rounded mr-2 hover:bg-yellow-600">Edit</button>
                   <button onClick={() => handleDelete(task.id)} className="bg-red-500 text-white font-bold shadow px-3 py-1 rounded hover:bg-red-600">Delete</button>
                 </div>
               </div>
             ))}
             {tasks.length === 0 && <p className="text-gray-500 italic">No tasks found.</p>}
           </div>
        </div>
      </div>
    </div>
  );
}
