package list;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import task.Task;
import user.User;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2022-05-21T16:18:25", comments="EclipseLink-2.7.9.v20210604-rNA")
@StaticMetamodel(TodoList.class)
public class TodoList_ { 

    public static volatile SingularAttribute<TodoList, String> name;
    public static volatile SingularAttribute<TodoList, Long> id;
    public static volatile SingularAttribute<TodoList, User> user;
    public static volatile ListAttribute<TodoList, Task> tasks;

}