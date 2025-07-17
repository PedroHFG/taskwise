-- Roles
INSERT INTO tb_role (authority) VALUES ('ROLE_USER');
INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN')

-- Users
INSERT INTO tb_user (name, email, password) VALUES ('Alex', 'alex@gmail.com', '$2a$12$nAYLC6f5cbjnG.Cc0fxCEO3ZAC1UnbuuP91UUAg4p9wHYlAL4tNQi')
INSERT INTO tb_user (name, email, password) VALUES ('Maria', 'maria@gmail.com', '$2a$12$nAYLC6f5cbjnG.Cc0fxCEO3ZAC1UnbuuP91UUAg4p9wHYlAL4tNQi')


-- Users - Roles
INSERT INTO tb_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 1);
INSERT INTO tb_user_role (user_id, role_id) VALUES (2, 2);

-- Tarefas Concluídas
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Revisar código do projeto X', 'Fazer uma revisão completa das últimas features implementadas.', '2025-07-05', TRUE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Organizar arquivos de impostos', 'Digitalizar e categorizar todos os documentos fiscais do ano.', '2025-06-20', TRUE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Responder e-mails pendentes', 'Limpar a caixa de entrada, responder a todos os e-mails importantes.', '2025-07-10', TRUE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Comprar suprimentos de escritório', 'Fazer lista de compras e adquirir os itens necessários.', '2025-07-12', TRUE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Configurar ambiente de dev', 'Instalar novas ferramentas e configurar IDE para novo projeto.', '2025-07-14', TRUE, NOW(), NOW(), 2);

-- Tarefas Não Concluídas (Futuras)
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Estudar microserviços com Spring Cloud', 'Aprofundar em Eureka, Zuul e Hystrix.', '2025-08-10', FALSE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Preparar apresentação para a reunião', 'Criar slides e roteiro para a reunião da próxima semana.', '2025-07-28', FALSE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Pesquisar novas tecnologias frontend', 'Explorar React, Vue.js e Angular para futuro projeto.', '2025-09-01', FALSE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Agendar consulta médica anual', 'Ligar para a clínica e marcar o check-up.', '2025-07-22', FALSE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Planejar férias de fim de ano', 'Definir destino, datas e orçamento.', '2025-10-01', FALSE, NOW(), NOW(), 2);

-- Tarefas Não Concluídas (Vencidas)
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Pagar conta de energia elétrica', 'Verificar vencimento e realizar pagamento online.', '2025-07-01', FALSE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Enviar relatório semanal', 'Compilar atividades da semana e enviar para o gestor.', '2025-07-07', FALSE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Consertar torneira pingando', 'Chamar encanador ou tentar resolver o problema.', '2025-06-25', FALSE, NOW(), NOW(), 1);

-- Tarefas Variadas para Teste
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Estudar SQL avançado', 'Aprender sobre otimização de queries e índices.', '2025-08-05', FALSE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Preparar o churrasco de domingo', 'Comprar carnes, carvão e bebidas.', '2025-07-21', FALSE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Ler livro "Clean Code"', 'Revisar princípios de boa escrita de código.', '2025-09-15', FALSE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Fazer exercícios físicos', 'Caminhada ou academia por 30 minutos.', '2025-07-19', FALSE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Atualizar portfólio online', 'Adicionar os últimos projetos desenvolvidos.', '2025-08-20', FALSE, NOW(), NOW(), 1);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Aprender Kotlin para Spring', 'Explorar a linguagem Kotlin e sua integração com Spring Boot.', '2025-10-10', FALSE, NOW(), NOW(), 2);
INSERT INTO tasks (title, description, due_date, completed, created_at, updated_at, user_id) VALUES ('Testar nova feature X', 'Realizar testes de integração e unitários da funcionalidade.', '2025-07-26', FALSE, NOW(), NOW(), 1);