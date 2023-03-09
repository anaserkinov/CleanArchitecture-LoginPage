import androidx.lifecycle.Observer
import com.undefined.appname.ui.main.MainViewModel
import com.undefined.domain.UIState
import com.undefined.domain.models.UIUser
import com.undefined.domain.repositories.AuthRepository
import com.undefined.domain.usecases.GetUserUseCase
import com.undefined.domain.usecases.LogoutUseCase
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
class MainViewModelTest {

    private lateinit var logoutUseCase: LogoutUseCase
    private lateinit var getUserUseCase: GetUserUseCase

    private lateinit var viewModel: MainViewModel

    @BeforeEach
    fun init(){
        logoutUseCase = Mockito.mock(
            LogoutUseCase::class.java,
            withSettings().useConstructor(Mockito.mock(AuthRepository::class.java))
        )
        getUserUseCase = Mockito.mock(
            GetUserUseCase::class.java,
            withSettings().useConstructor(Mockito.mock(AuthRepository::class.java))
        )
        viewModel = MainViewModel(getUserUseCase, logoutUseCase)
    }

    @Test
    fun loadUser_Test(){
        runTest {
            Mockito.`when`(getUserUseCase.invoke()).thenReturn(
                UIState.Success(
                    UIUser(
                        "id",
                        "name",
                        "email"
                    )
                )
            )
            viewModel.loadUser()
            val value = viewModel.user.getOrAwaitValue()
            assert(value is UIState.Success)
            assert((value as UIState.Success<UIUser>).data!!.id == "id"){"id didn't match"}
        }
    }

    @Test
    fun logout_Test(){
        runTest {
            Mockito.`when`(logoutUseCase.invoke()).thenReturn(
                UIState.Success()
            )
            viewModel.logout()
            assert(viewModel.logoutState.getOrAwaitValue() is UIState.Success)
        }
    }

}