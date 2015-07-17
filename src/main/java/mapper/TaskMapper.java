package mapper;

import java.util.List;
import java.util.Map;

public interface TaskMapper {
	public List getTaskList(Map param);
	public Integer insertTask(Map param);
	public void deleteTask(Map param);
	public Integer updateTask(Map param);
	public Integer updateTaskStatus(Map param);
	public Integer insertProcess(Map param);
	public Map getTaskById(Map param); 
	public Map getProcessInfo(Map param);
	public Integer updateProcessStatus(Map param);
}
