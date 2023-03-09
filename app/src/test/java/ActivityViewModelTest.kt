import androidx.lifecycle.Observer
import com.undefined.appname.ui.ActivityViewModel
import com.undefined.domain.repositories.ActivityRepository
import com.undefined.domain.usecases.AuthStateUseCase
import extentions.InstantTaskExecutorExtension
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

@ExtendWith(MockitoExtension::class, InstantTaskExecutorExtension::class)
class ActivityViewModelTest {

    private lateinit var authStateUseCase: AuthStateUseCase
    private lateinit var viewModel: ActivityViewModel

    @BeforeEach
    fun init(){
       authStateUseCase = Mockito.mock(
           AuthStateUseCase::class.java,
           withSettings().useConstructor(Mockito.mock(ActivityRepository::class.java))
       )
        viewModel = ActivityViewModel(authStateUseCase)
    }

    @Test
    fun loadAppState_Test(){
        Mockito.`when`(authStateUseCase.invoke()).thenReturn(true)
        viewModel.loadAppState()
        val value = viewModel.userLoggedIn.getOrAwaitValue()
        assert(value)
    }


}