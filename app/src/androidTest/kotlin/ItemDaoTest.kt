import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mysql.data.Item
import com.example.mysql.data.ItemDao
import com.example.mysql.data.ItemDatabase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ItemDaoTest {
    private lateinit var itemDao: ItemDao
    private lateinit var inventoryDatabase: ItemDatabase

    @Before
    fun createDB(){
        val context: Context = ApplicationProvider.getApplicationContext()
        inventoryDatabase = Room.inMemoryDatabaseBuilder(context, ItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        itemDao = inventoryDatabase.itemDao()
    }

    @After
    fun closeDB(){
        inventoryDatabase.close()
    }

    private var item1 = Item(1, "Banana", "2,00", 2)
    private var item2 = Item(2, "Maça", "3,00", 3)

    private suspend fun AdicionarItem(){
        itemDao.insert(item1)
    }

    private suspend fun AdicionarItens(){
        itemDao.insert(item1)
        itemDao.insert(item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoInsert_insertsItemIntoDB() = runBlocking {
        AdicionarItem()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
    }

    @Test
    @Throws(Exception::class)
    fun daoGetAllItems_returnsAllItemsFromDB() = runBlocking {
        AdicionarItens()
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], item1)
        assertEquals(allItems[1], item2)
    }

    @Test
    @Throws(Exception::class)
    fun daoUpdateItems_updatesItemsInDB() = runBlocking {
        AdicionarItens()
        itemDao.update(Item(1, "Banana", "2,00", 2))
        itemDao.update(Item(2, "Maça", "3,00", 3))
        val allItems = itemDao.getAllItems().first()
        assertEquals(allItems[0], Item(1, "Banana", "2,00", 2))
        assertEquals(allItems[1], Item(2, "Maça", "3,00", 3))
    }

    @Test
    @Throws(Exception::class)
    fun daoDeleteItems_deletesAllItemsFromDB() = runBlocking {
        AdicionarItens()
        itemDao.delete(item1)
        itemDao.delete(item2)
        val allItems = itemDao.getAllItems().first()
        assertTrue(allItems.isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun daoGetItem_returnsItemFromDB() = runBlocking {
        AdicionarItem()
        val item = itemDao.getItem(1)
        assertEquals(item.first(), item1)
    }
}