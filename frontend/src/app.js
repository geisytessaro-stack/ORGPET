import { createApp } from 'vue/dist/vue.esm-bundler.js';

createApp({
    data() {
        return {
            aba: 'tutores',
            tutores: [],
            animais: [],
            vacinas: [],
            mensagem: '',
            tipoMensagem: 'sucesso',
            tutorForm: { id: null, nome: '', telefone: '' },
            animalForm: { id: null, nome: '', raca: '', idade: null, saude: '', tutorId: '' },
            vacinaForm: { id: null, nome: '', dataVacina: '', animalId: '' }
        };
    },
    async mounted() {
        await this.carregarTudo();
    },
    methods: {
        async requisicao(url, opcoes = {}) {
            const resposta = await fetch(url, {
                headers: { 'Content-Type': 'application/json' },
                ...opcoes
            });
            if (!resposta.ok) throw new Error(`Erro ${resposta.status}`);
            return resposta.status === 204 ? null : resposta.json();
        },
        async carregarTudo() {
            try {
                [this.tutores, this.animais, this.vacinas] = await Promise.all([
                    this.requisicao('/tutores'),
                    this.requisicao('/animais'),
                    this.requisicao('/vacinas')
                ]);
            } catch (erro) {
                this.avisar('Não foi possível carregar os dados. Confira a conexão com o banco.', 'erro');
            }
        },
        mudarAba(aba) {
            this.aba = aba;
            this.mensagem = '';
        },
        avisar(texto, tipo = 'sucesso') {
            this.mensagem = texto;
            this.tipoMensagem = tipo;
            window.setTimeout(() => { if (this.mensagem === texto) this.mensagem = ''; }, 3500);
        },
        inicial(nome) {
            return nome ? nome.charAt(0).toUpperCase() : '?';
        },
        formatarData(data) {
            if (!data) return 'Data não informada';
            return new Intl.DateTimeFormat('pt-BR', { timeZone: 'UTC' }).format(new Date(`${data}T00:00:00Z`));
        },
        async salvarTutor() {
            try {
                const editando = !!this.tutorForm.id;
                const url = editando ? `/tutores/${this.tutorForm.id}` : '/tutores';
                await this.requisicao(url, { method: editando ? 'PUT' : 'POST', body: JSON.stringify(this.tutorForm) });
                this.limparTutor();
                await this.carregarTudo();
                this.avisar(`Tutor ${editando ? 'atualizado' : 'cadastrado'} com sucesso!`);
            } catch (erro) { this.avisar('Não foi possível salvar o tutor.', 'erro'); }
        },
        editarTutor(tutor) {
            this.tutorForm = { id: tutor.id, nome: tutor.nome, telefone: tutor.telefone || '' };
            window.scrollTo({ top: 250, behavior: 'smooth' });
        },
        limparTutor() { this.tutorForm = { id: null, nome: '', telefone: '' }; },
        async salvarAnimal() {
            try {
                const editando = !!this.animalForm.id;
                const corpo = {
                    nome: this.animalForm.nome, raca: this.animalForm.raca,
                    idade: this.animalForm.idade, saude: this.animalForm.saude,
                    tutor: { id: this.animalForm.tutorId }
                };
                await this.requisicao(editando ? `/animais/${this.animalForm.id}` : '/animais', {
                    method: editando ? 'PUT' : 'POST', body: JSON.stringify(corpo)
                });
                this.limparAnimal();
                await this.carregarTudo();
                this.avisar(`Animal ${editando ? 'atualizado' : 'cadastrado'} com sucesso!`);
            } catch (erro) { this.avisar('Não foi possível salvar o animal.', 'erro'); }
        },
        editarAnimal(animal) {
            this.animalForm = { id: animal.id, nome: animal.nome, raca: animal.raca || '', idade: animal.idade, saude: animal.saude || '', tutorId: animal.tutor?.id || '' };
            window.scrollTo({ top: 250, behavior: 'smooth' });
        },
        limparAnimal() { this.animalForm = { id: null, nome: '', raca: '', idade: null, saude: '', tutorId: '' }; },
        async salvarVacina() {
            try {
                const editando = !!this.vacinaForm.id;
                const corpo = { nome: this.vacinaForm.nome, dataVacina: this.vacinaForm.dataVacina || null, animal: { id: this.vacinaForm.animalId } };
                await this.requisicao(editando ? `/vacinas/${this.vacinaForm.id}` : '/vacinas', {
                    method: editando ? 'PUT' : 'POST', body: JSON.stringify(corpo)
                });
                this.limparVacina();
                await this.carregarTudo();
                this.avisar(`Vacina ${editando ? 'atualizada' : 'cadastrada'} com sucesso!`);
            } catch (erro) { this.avisar('Não foi possível salvar a vacina.', 'erro'); }
        },
        editarVacina(vacina) {
            this.vacinaForm = { id: vacina.id, nome: vacina.nome, dataVacina: vacina.dataVacina || '', animalId: vacina.animal?.id || '' };
            window.scrollTo({ top: 250, behavior: 'smooth' });
        },
        limparVacina() { this.vacinaForm = { id: null, nome: '', dataVacina: '', animalId: '' }; },
        async excluir(recurso, id) {
            if (!window.confirm('Deseja realmente excluir este registro?')) return;
            try {
                await this.requisicao(`/${recurso}/${id}`, { method: 'DELETE' });
                await this.carregarTudo();
                this.avisar('Registro excluído com sucesso!');
            } catch (erro) {
                this.avisar('Não foi possível excluir. O registro pode estar sendo usado em outro cadastro.', 'erro');
            }
        }
    }
}).mount('#app');
