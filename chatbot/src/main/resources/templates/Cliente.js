document.addEventListener('DOMContentLoaded', function () {
    const telefoneInput = document.getElementById('search');
    const searchButton = document.getElementById('searchButton');
    let telefoneAtual = ""; // Variável para armazenar o número de telefone
    let pedidoId = null; // Armazena o ID do pedido para cancelamento

    searchButton.addEventListener('click', function () {
        const Ntelefone = telefoneInput.value.trim();
        if (Ntelefone) {
            telefoneAtual = Ntelefone; // Armazena o telefone para atualizações
            console.log("Telefone Atual: ", telefoneAtual);
            buscarTelefone(Ntelefone);
        } else {
            alert('Por favor, insira um número de telefone válido.');
        }
    });

    // Atualiza o conteúdo do modal a cada 10 segundos, chamando a função buscarTelefone
    setInterval(() => {
        if (telefoneAtual) {
            buscarTelefone(telefoneAtual);
        }
    }, 10000); // 10 segundos

});

function buscarTelefone(numero) {
    fetch(`http://localhost:8080/GetNumero?numero=${encodeURIComponent(numero)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Erro ao buscar número de telefone (status ${response.status}): ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            console.log('Dados do número de telefone:', data);
            if (data && data.length > 0) {
                const pedido = data[0]; // Supondo que o primeiro item contém o pedido
                pedidoId = pedido.idpedido; // Agora, armazenando corretamente o ID do pedido

                const statusColors = {
                    Pendente: "orange",
                    Finalizado: "green",
                    Cancelado: "red",
                };

                const orderDetailsElement = document.getElementById('orderDetails');

                const isDarkMode = document.body.classList.contains('dark-mode');
                const titleColor = 'black';
                const valueColor = isDarkMode ? 'black' : 'inherit';

                orderDetailsElement.innerHTML = `
                    <div style="background-color: #e0e0e0;">
                        <h3>Pedido #${pedido.numero}</h3>
                        <p><strong style="color: ${titleColor};">Nome:</strong> <span style="color: ${valueColor};">${pedido.nome}</span></p>
                        <p><strong style="color: ${titleColor};">Item Pedido:</strong> <span style="color: ${valueColor};">${pedido.intemPedido}</span></p>
                        <p><strong style="color: ${titleColor};">Total a Pagar:</strong> <span style="color: ${valueColor};">${pedido.total.toLocaleString("pt-BR", { style: "currency", currency: "BRL" })}</span></p>
                        <p><strong style="color: ${titleColor};">Forma de Pagamento:</strong> <span style="color: ${valueColor};">${pedido.formaDepagamneto}</span></p>
                        <p>
                            <strong style="color: ${titleColor};">Status:</strong> 
                            <span style="color: ${statusColors[pedido.status] || 'black'};">
                                ${pedido.status}
                            </span>
                        </p>
                        <p><strong style="color: ${titleColor};">Data e Hora de Recebimento:</strong> <span style="color: ${valueColor};">${new Date(pedido.dataHoraRecebimento).toLocaleString()}</span></p>
                    </div>
                `;

                const modal = document.getElementById('orderModal');
                modal.style.display = 'block';

                const closeModal = document.getElementById('closeModal');
                closeModal.onclick = () => {
                    modal.style.display = 'none';
                };
            } else {
                alert('Nenhum pedido encontrado para este número.');
            }
        })
        .catch(error => {
            console.error('Erro ao buscar número de telefone:', error);
            alert('Erro ao carregar os dados: ' + error.message);
        });
}

function cancelarPedido() {
    if (!pedidoId) {
        alert('Nenhum pedido encontrado para cancelar.');
        return;
    }

    console.log(`Tentando cancelar pedido com ID: ${pedidoId}`);

    fetch(`http://localhost:8080/api/pedidos/${pedidoId}/cancelar`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text);
                });
            }
            return response.text();
        })
        .then(data => {
            console.log('Pedido cancelado:', data);
            alert('Pedido cancelado com sucesso.');
            document.getElementById('orderModal').style.display = 'none';
        })
        .catch(error => {
            console.error('Erro ao cancelar pedido:', error);
            alert('Erro ao cancelar o pedido: ' + error.message);
        });
}

// Temporizador de 5 minutos para cancelamento automático do pedido
let remainingTime = 50; // 5 minutos em segundos
const countdownElement = document.getElementById('countdown');

const timerInterval = setInterval(() => {
    const minutes = Math.floor(remainingTime / 60);
    const seconds = remainingTime % 60;

    countdownElement.textContent = `${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;

    if (remainingTime <= 0) {
        clearInterval(timerInterval); // Para o temporizador
        alert('O tempo acabou! O pedido foi cancelado.');
        cancelarPedido(); // Cancela o pedido automaticamente
    }

    remainingTime--;
}, 1000);

// Alternar tema (modo escuro)
document.getElementById("theme-toggle").addEventListener("click", function () {
    document.body.classList.toggle("dark-mode");
});
