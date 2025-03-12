Ótima observação! A partir do Spring Batch 5, o uso de JobBuilderFactory e StepBuilderFactory foi depreciado. Agora, a abordagem correta é usar JobBuilder e StepBuilder diretamente via JobRepository.

Aqui está a versão atualizada do código usando os novos builders corretamente.


---

Passo 1: Configurando os Readers

Aqui estão os leitores para EntityA e EntityB usando JpaPagingItemReader.

Leitor para EntityA

@Bean
public JpaPagingItemReader<EntityA> entityAItemReader(EntityManagerFactory entityManagerFactory) {
    return new JpaPagingItemReaderBuilder<EntityA>()
            .name("entityAReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT e FROM EntityA e")
            .pageSize(10)
            .build();
}

Leitor para EntityB

@Bean
public JpaPagingItemReader<EntityB> entityBItemReader(EntityManagerFactory entityManagerFactory) {
    return new JpaPagingItemReaderBuilder<EntityB>()
            .name("entityBReader")
            .entityManagerFactory(entityManagerFactory)
            .queryString("SELECT e FROM EntityB e")
            .pageSize(10)
            .build();
}


---

Passo 2: Criando o CompositeItemReader

Precisamos combinar os leitores.

@Bean
public CompositeItemReader<Pair<EntityA, EntityB>> compositeItemReader(
        JpaPagingItemReader<EntityA> entityAItemReader,
        JpaPagingItemReader<EntityB> entityBItemReader) {
    
    CompositeItemReader<Pair<EntityA, EntityB>> reader = new CompositeItemReader<>();
    reader.setDelegates(List.of(entityAItemReader, entityBItemReader));
    return reader;
}


---

Passo 3: Criando o Processador

O processador irá transformar os dados.

@Bean
public ItemProcessor<Pair<EntityA, EntityB>, EntityC> processor() {
    return pair -> {
        EntityA entityA = pair.getLeft();
        EntityB entityB = pair.getRight();
        
        EntityC entityC = new EntityC();
        entityC.setFieldA(entityA.getSomeField());
        entityC.setFieldB(entityB.getAnotherField());
        
        return entityC;
    };
}


---

Passo 4: Criando o Writer

O escritor salvará os novos registros no banco.

@Bean
public JpaItemWriter<EntityC> writer(EntityManagerFactory entityManagerFactory) {
    JpaItemWriter<EntityC> writer = new JpaItemWriter<>();
    writer.setEntityManagerFactory(entityManagerFactory);
    return writer;
}


---

Passo 5: Criando o Step com o Novo StepBuilder

Agora, criamos o Step sem StepBuilderFactory, utilizando o novo StepBuilder.

@Bean
public Step step1(JobRepository jobRepository,
                  PlatformTransactionManager transactionManager,
                  CompositeItemReader<Pair<EntityA, EntityB>> reader,
                  ItemProcessor<Pair<EntityA, EntityB>, EntityC> processor,
                  JpaItemWriter<EntityC> writer) {

    return new StepBuilder("step1", jobRepository)
            .<Pair<EntityA, EntityB>, EntityC>chunk(10, transactionManager)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
}


---

Passo 6: Criando o Job com o Novo JobBuilder

Agora, criamos o Job sem JobBuilderFactory, utilizando JobBuilder diretamente.

@Bean
public Job job(JobRepository jobRepository, Step step1) {
    return new JobBuilder("jobExample", jobRepository)
            .incrementer(new RunIdIncrementer())
            .start(step1)
            .build();
}


---

Resumo das Melhorias

Removemos StepBuilderFactory e JobBuilderFactory, substituindo pelos novos builders (StepBuilder e JobBuilder).

Usamos JobRepository para criar JobBuilder e StepBuilder, conforme exigido no Spring Batch 5+.

Garantimos compatibilidade com transações passando PlatformTransactionManager ao chunk.


Agora, o código está atualizado e segue as boas práticas do Spring Batch 5+! 🚀

