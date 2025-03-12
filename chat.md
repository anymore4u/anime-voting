Ótimo ponto! Como você está usando Java 17 (onde não há Pair) e Spring Batch 5.1.1 (onde CompositeItemReader foi removido), precisamos de uma abordagem diferente para lidar com a leitura de duas fontes de dados.

Solução Alternativa

Em vez de usar CompositeItemReader, criamos um Custom ItemReader que lê e combina os dados manualmente.


---

Passo 1: Criando um Custom ItemReader

Esse leitor buscará dados de EntityA e EntityB e os combinará em um DTO (EntityCombinedDTO).

@Component
@StepScope
public class EntityCombinedItemReader implements ItemReader<EntityCombinedDTO> {

    private final EntityManager entityManager;
    private final List<EntityA> entityAList;
    private final List<EntityB> entityBList;
    private int index = 0;

    @Autowired
    public EntityCombinedItemReader(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.entityAList = fetchEntityA();
        this.entityBList = fetchEntityB();
    }

    private List<EntityA> fetchEntityA() {
        return entityManager.createQuery("SELECT e FROM EntityA e", EntityA.class).getResultList();
    }

    private List<EntityB> fetchEntityB() {
        return entityManager.createQuery("SELECT e FROM EntityB e", EntityB.class).getResultList();
    }

    @Override
    public EntityCombinedDTO read() {
        if (index >= entityAList.size() || index >= entityBList.size()) {
            return null; // Fim da leitura
        }

        EntityA entityA = entityAList.get(index);
        EntityB entityB = entityBList.get(index);

        EntityCombinedDTO dto = new EntityCombinedDTO(entityA, entityB);
        index++;
        return dto;
    }
}

Aqui, EntityCombinedDTO é um DTO que armazena os dados combinados:

public class EntityCombinedDTO {
    private final EntityA entityA;
    private final EntityB entityB;

    public EntityCombinedDTO(EntityA entityA, EntityB entityB) {
        this.entityA = entityA;
        this.entityB = entityB;
    }

    public EntityA getEntityA() {
        return entityA;
    }

    public EntityB getEntityB() {
        return entityB;
    }
}


---

Passo 2: Criando o Processador

O processador transformará o DTO em EntityC.

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

Passo 3: Criando o Writer

Usamos um JpaItemWriter para salvar EntityC no banco.

@Bean
public JpaItemWriter<EntityC> writer(EntityManagerFactory entityManagerFactory) {
    JpaItemWriter<EntityC> writer = new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
}


---

Passo 4: Criando o Step

Usamos o novo StepBuilder.

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

Passo 5: Criando o Job

@Bean
public Job job(JobRepository jobRepository, Step step1) {
    return new JobBuilder("jobExample", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(step1)
            .build();
}


---

Conclusão

Essa abordagem:

Elimina a necessidade de Pair criando um DTO próprio (EntityCombinedDTO).

Evita o CompositeItemReader, substituindo por um ItemReader personalizado.

Garante compatibilidade com Spring Batch 5.1.1 e Java 17.


Agora o batch está totalmente compatível com a versão que você usa! 🚀

