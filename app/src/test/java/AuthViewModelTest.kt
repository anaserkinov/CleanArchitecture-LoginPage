import androidx.lifecycle.Observer
import com.undefined.appname.ui.auth.AuthViewModel
import com.undefined.domain.UIState
import com.undefined.domain.repositories.AuthRepository
import com.undefined.domain.usecases.LoginUseCase
import extentions.InstantTaskExecutorExtension
import extentions.MainDispatcherExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.withSettings

/**
 * Created by Anaskhan on 09/03/23.
 **/

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MockitoExtension::class, InstantTaskExecutorExtension::class, MainDispatcherExtension::class)
class AuthViewModelTest {

    private lateinit var loginUseCase: LoginUseCase
    private lateinit var viewModel: AuthViewModel

    @BeforeEach
    fun init(){
        loginUseCase = Mockito.mock(
            LoginUseCase::class.java,
            withSettings().useConstructor(Mockito.mock(AuthRepository::class.java))
        )
        viewModel = AuthViewModel(loginUseCase)
    }

    @Test
    fun login_Test(){
        runTest {
            Mockito.`when`(loginUseCase.invoke("", "")).thenReturn(
                UIState.Success()
            )
            viewModel.login("", "")
            assert(viewModel.loginState.getOrAwaitValue() is UIState.Success)
        }
    }

}