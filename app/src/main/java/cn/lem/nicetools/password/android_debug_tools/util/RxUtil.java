package cn.lem.nicetools.password.android_debug_tools.util;

import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.CompletableSource;
import io.reactivex.rxjava3.core.CompletableTransformer;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.core.SingleTransformer;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.FlowableTransformer;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by codeest on 2016/8/3.
 */
public class RxUtil {

  /**
   * 统一线程处理
   */
  public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
    return new FlowableTransformer<T, T>() {
      @Override
      public Flowable<T> apply(Flowable<T> observable) {
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public static <T> SingleTransformer<T, T> rxSingleSchedulerHelper() {
    return new SingleTransformer<T, T>() {
      @Override
      public SingleSource<T> apply(Single<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public static <T> ObservableTransformer<T, T> rxObserverableSchedulerHelper() {
    return new ObservableTransformer<T, T>() {
      @Override public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  public static CompletableTransformer rxCompletableSchedulerHelper() {
    return new CompletableTransformer() {
      @Override public CompletableSource apply(Completable upstream) {
        return upstream.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
      }
    };
  }

  /**
   * 生成Flowable
   */
  public static <T> Flowable<T> createFlowable(final T t) {
    return Flowable.create(new FlowableOnSubscribe<T>() {
      @Override
      public void subscribe(FlowableEmitter<T> emitter) throws Exception {
        try {
          emitter.onNext(t);
          emitter.onComplete();
        } catch (Exception e) {
          emitter.onError(e);
        }
      }
    }, BackpressureStrategy.BUFFER);
  }

  /**
   * 生成Single
   */
  public static <T> Single<T> createSingle(final T t) {
    return Single.create(new SingleOnSubscribe<T>() {
      @Override public void subscribe(SingleEmitter<T> emitter) throws Exception {
        emitter.onSuccess(t);
      }
    });
  }

  /**
   * 生成一个成功的Completable
   */
  public static Completable createSuccessCompletable() {
    return Completable.create(new CompletableOnSubscribe() {
      @Override public void subscribe(CompletableEmitter emitter) throws Exception {
        emitter.onComplete();
      }
    });
  }
}
