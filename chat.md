Ótima escolha! Se vamos usar RepositoryItemReader para paginar os dados, precisamos configurar um leitor para cada entidade (EntityA e EntityB) e depois combiná-los logicamente no nosso ItemReader personalizado.


---

Solução Atualizada com RepositoryItemReader

Vamos dividir o processo em três partes principais:

1. Criamos dois RepositoryItemReader para EntityA e EntityB (com paginação).


2. Criamos um Custom ItemReader para ler e combinar os dados sincronizando a paginação.


3. Mantemos o Processador e o Writer, como antes.




---

Passo 1: Criando os RepositoryItemReader

Primeiro, precisamos dos repositórios das entidades.

Repositório de EntityA

@Repository
public interface EntityARepository extends PagingAndSortingRepository<EntityA, Long> {
}

Repositório de EntityB

@Repository
public interface EntityBRepository extends PagingAndSortingRepository<EntityB, Long> {
}

Agora, criamos os RepositoryItemReader para cada entidade.

Leitor para EntityA

@Bean
public RepositoryItemReader<EntityA> entityAItemReader(EntityARepository entityARepository) {
    RepositoryItemReader<EntityA> reader = new RepositoryItemReader<>();
    reader.setRepository(entityARepository);
    reader.setMethodName("findAll");
    reader.setPageSize(10);
    reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
    return reader;
}

Leitor para EntityB

@Bean
public RepositoryItemReader<EntityB> entityBItemReader(EntityBRepository entityBRepository) {
    RepositoryItemReader<EntityB> reader = new RepositoryItemReader<>();
    reader.setRepository(entityBRepository);
    reader.setMethodName("findAll");
    reader.setPageSize(10);
    reader.setSort(Collections.singletonMap("id", Sort.Direction.ASC));
    return reader;
}


---

Passo 2: Criando um Custom ItemReader para Combinar os Dados

Agora, precisamos de um ItemReader que combine os dados paginados das duas entidades.

@Component
@StepScope
public class EntityCombinedItemReader implements ItemReader<EntityCombinedDTO> {

    private final RepositoryItemReader<EntityA> entityAReader;
    private final RepositoryItemReader<EntityB> entityBReader;

    @Autowired
    public EntityCombinedItemReader(RepositoryItemReader<EntityA> entityAReader, 
                                    RepositoryItemReader<EntityB> entityBReader) {
        this.entityAReader = entityAReader;
        this.entityBReader = entityBReader;
    }

    @Override
    public EntityCombinedDTO read() throws Exception {
        EntityA entityA = entityAReader.read();
        EntityB entityB = entityBReader.read();

        if (entityA == null || entityB == null) {
            return null; // Fim da leitura
        }

        return new EntityCombinedDTO(entityA, entityB);
    }
}


---

Passo 3: Criando o Processador

O processador transformará os dados no formato final (EntityC).

@Bean
public ItemProcessor<EntityCombinedDTO, EntityC> processor() {
    return dto -> {
        EntityA entityA = dto.getEntityA();
        EntityB entityB = dto.getEntityB();
        
        EntityC entityC = new EntityC();
        entityC.setFieldA(entityA.getSomeField());
        entityC.setFieldB(entityB.getAnotherField());

        return entityC;
    };
}


---

Passo 4: Criando o Writer

O writer salvará os novos registros no banco.

@Bean
public JpaItemWriter<EntityC> writer(EntityManagerFactory entityManagerFactory) {
    JpaItemWriter<EntityC> writer = new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
}


---

Passo 5: Criando o Step

Agora, criamos o Step sem StepBuilderFactory, utilizando o novo StepBuilder.

@Bean
public Step step1(JobRepository jobRepository,
                  PlatformTransactionManager transactionManager,
                  EntityCombinedItemReader reader,
                  ItemProcessor<EntityCombinedDTO, EntityC> processor,
                  JpaItemWriter<EntityC> writer) {

    return new StepBuilder("step1", jobRepository)
            .<EntityCombinedDTO, EntityC>chunk(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
}


---

Passo 6: Criando o Job

@Bean
public Job job(JobRepository jobRepository, Step step1) {
    return new JobBuilder("jobExample", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(step1)
            .build();
}


---

Conclusão

Agora temos um Job Spring Batch 5.1.1 que: ✅ Usa RepositoryItemReader com paginação para EntityA e EntityB.
✅ Cria um Custom ItemReader que sincroniza a leitura das duas fontes.
✅ Processa os dados em um DTO (EntityCombinedDTO) antes de salvar.
✅ Escreve na EntityC corretamente.

Essa abordagem é 100% compatível com Java 17 e Spring Batch 5.1.1! 🚀

