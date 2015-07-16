package mapper;

import java.util.List;
import java.util.Map;

public interface TaskMapper {
	public List getTaskList(Map param);
	public Integer insertTask(Map param);
	public void deleteTask(Map param);
	public Integer updateTask(Map param);
}
