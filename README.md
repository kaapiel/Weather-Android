# Reports App #

Este é o repositório do código fonte do app dashboard para android.

### Contribua conosco ###

* A nossa política de code review está em construção

## 1 - Ferramentas e tecnologias ##

Estas são as tecnologias e estruturas que utilizamos no projeto:
- Android/Java 8
- Gradle
- MPChartLib
			
## 2 - Resultados de gráficos ##

Todas as informações geradas neste app está baseada no Jira, Jenkins, ALM e SonarQube.
* O Score do sistema (Half Pie) é gerado baseado no cálculo da média de todas as mpetricas do sistema.
* O histórico do Score (Line Chart) é beaseado na nota que o sistema tem ao longo do tempo.
* Os tipos de issues (Horizontal bar chart) são obtidos do SonarQube.
* Os issues pro criticidade (Bar chart) são obtidos do SonarQube.

## 3 - Pre requisitos ##

* 1 - Para poder utilizar o app, serao nessarias as suas credenciais (usuário e senha).
* 2 - É necessario estar conectado na rede interna da empresa (SIGMA)
* 3 - É necessário estar inserido no grupo de firewall G_WIFI_SIGMA