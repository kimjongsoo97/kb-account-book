package context;

import lombok.Getter;
import lombok.Setter;
import org.account.domain.UserVO;

@Getter
@Setter
public class Context {
private UserVO user;
private static Context context = new Context();
public static Context getContext() {
    return context;
}
}