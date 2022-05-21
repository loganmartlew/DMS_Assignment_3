package task;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import list.TodoList;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-05-21T16:18:25", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(Task.class)
public class Task_ { 

    public static volatile SingularAttribute<Task, TodoList> todoList;
    public static volatile SingularAttribute<Task, String> name;
    public static volatile SingularAttribute<Task, Long> id;
    public static volatile SingularAttribute<Task, Boolean> isComplete;

}