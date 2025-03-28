// Pacote do projeto
package br.com.ibm.supertasks

// Imports de bibliotecas e pacotes
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.* // Importamos para usar Column, Row, etc
import androidx.compose.material3.* // Importamos para usar Button, OutlinedTextField, etc
import androidx.compose.runtime.* // Importamos para usar remember e mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.ibm.supertasks.ui.theme.SuperTasksTheme
import com.google.firebase.firestore.ktx.firestore // Para acessar o Firestore
import com.google.firebase.ktx.Firebase

// Definindo a Activity principal do aplicativo (tela principal do aplicativo)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperTasksTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Chamando a função TaskApp que constrói o conteúdo principal do aplicativo
                    TasksApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Criando a função saveNewTask que adiciona os dados no Firestore
fun saveNewTask(name: String, description: String) {

    // Obtendo a instância do banco de dados na nuvem (Firestore)
    val db = Firebase.firestore

    // Criando o documento que irá armazenar os dados
    val newTask = hashMapOf(
        "name" to name,
        "description" to description
    )

    // Enviando os dados para o banco de dados
    db.collection("tasks").add(newTask)
}

@Composable
// Criando a função FormTasksApp que irá construir o formulário onde o usuário pode inserir os seus dados
fun FormTasksApp(modifier: Modifier = Modifier) {

    // Criando variáveis para guardar o que o usuário digitar
    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }

    // Cria uma coluna (uma lista vertical) onde todos os elementos serão colocados um abaixo do outro
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Exibe o texto "Salvar" na tela
        Text(
            text = "Salvar",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        // Cria um campo de texto onde o usuário pode digitar o seu nome
        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = nome,
            onValueChange = { nome = it },
            label = { Text(text = "Nome") }
        )

        // Cria um campo de texto onde o usuário pode digitar a sua descrição
        OutlinedTextField(
            modifier = Modifier.padding(10.dp),
            value = descricao,
            onValueChange = { descricao = it },
            label = { Text(text = "Descricao") }
        )

        // Cria um botão para salvar as informações do usuário
        Button(
            onClick = {
                // Chama a função que salva a tarefa no Firestore, passando os dados digitados
                saveNewTask(nome, descricao)
            }
        ) {
            // Exibe o texto dentro do botão
            Text(text = "Salvar")
        }
    }
}

@Preview(showBackground = true)
@Composable

// Cria a função TasksApp que é responsável por agrupar e organiza o conteúdo principal da tela
fun TasksApp(modifier: Modifier = Modifier) {

    // Chama a função FormTasksApp
    FormTasksApp(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    )
}